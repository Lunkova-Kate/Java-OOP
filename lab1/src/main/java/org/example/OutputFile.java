package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.List;

public class OutputFile {
    private String outputFile;

    public OutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void writeReport(List<WordStat> stats) {
        long totalWords = stats.stream().mapToLong(WordStat::getFrequency).sum();
        String header = "Слово;Частота;Частота (%)\n";

        try (Writer writer = new FileWriter(outputFile)) {
            writer.write(header); // Записываем заголовки столбцов

            DecimalFormat df = new DecimalFormat("#.###"); // Форматируем проценты до двух знаков после запятой

            // проходим по каждому слову и записываем его вместе с частотой и процентом
            for (WordStat stat : stats) {
                double percentage = (double) stat.getFrequency() / totalWords * 100;
                String row = stat.getWord() + ";" +
                        stat.getFrequency() + ";" +
                        df.format(percentage) + "%\n"; // Формируем строку для записи
                writer.write(row);
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + outputFile);
        }
    }
}