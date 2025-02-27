package org.example;

public class WordStat implements Comparable<WordStat> {
    private String word;
    private int frequency;

    public WordStat(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(WordStat other) {
        return Integer.compare(other.frequency, this.frequency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordStat wordStat = (WordStat) o;
        return word.equals(wordStat.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    public void incrementFrequency() {
        frequency++;
    }
}