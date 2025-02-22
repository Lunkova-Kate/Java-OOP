package org.example;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class ReportGenerator {
    private Map<String, WordStat> wordStats;

    public ReportGenerator() {
        wordStats = new HashMap<>();
    }

    public void analyze(Reader file) throws IOException {
        int symbol;
        StringBuilder letters = new StringBuilder();

        while ((symbol = file.read()) != -1) {
            if (Character.isLetterOrDigit((char) symbol)) {
                letters.append((char) symbol);
            } else if (letters.length() > 0) {
                processWord(letters.toString());
                letters.setLength(0); // чистим для следующего слова
            }
        }

        // обработка последнего слова, если оно есть
        if (letters.length() > 0) {
            processWord(letters.toString());
        }
    }

    private void processWord(String word) {
        word = word.toLowerCase();
        wordStats.putIfAbsent(word, new WordStat(word, 0));
        wordStats.get(word).incrementFrequency();
    }

    public List<WordStat> getSortedWordStats() {
        List<WordStat> sortedStats = new ArrayList<>(wordStats.values());
        Collections.sort(sortedStats);
        return sortedStats;
    }
}