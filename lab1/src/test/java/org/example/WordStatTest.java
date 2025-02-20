package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordStatTest {

    @Test
    void testConstructorAndGetters() {

        String word = "hello";
        int frequency = 5;

        WordStat wordStat = new WordStat(word, frequency);

        assertEquals(word, wordStat.getWord());
        assertEquals(frequency, wordStat.getFrequency());
    }

    @Test
    void testCompareTo() {

        WordStat wordStat1 = new WordStat("hello", 5);
        WordStat wordStat2 = new WordStat("world", 3);
        WordStat wordStat3 = new WordStat("java", 5);


        assertTrue(wordStat1.compareTo(wordStat2) < 0); // 5 > 3
        assertTrue(wordStat2.compareTo(wordStat1) > 0); // 3 < 5
        assertEquals(0, wordStat1.compareTo(wordStat3)); // 5 == 5
    }

    @Test
    void testEquals() {

        WordStat wordStat1 = new WordStat("hello", 5);
        WordStat wordStat2 = new WordStat("hello", 5);
        WordStat wordStat3 = new WordStat("world", 3);

        assertEquals(wordStat1, wordStat2); // Объекты с одинаковым словом равны
        assertNotEquals(wordStat1, wordStat3); // Объекты с разными словами не равны
    }

    @Test
    void testHashCode() {

        WordStat wordStat1 = new WordStat("hello", 5);
        WordStat wordStat2 = new WordStat("hello", 5);
        WordStat wordStat3 = new WordStat("world", 3);

        assertEquals(wordStat1.hashCode(), wordStat2.hashCode()); // Хэш-коды равны для одинаковых слов
        assertNotEquals(wordStat1.hashCode(), wordStat3.hashCode()); // Хэш-коды разные для разных слов
    }

    @Test
    void testIncrementFrequency() {

        WordStat wordStat = new WordStat("hello", 5);

        wordStat.incrementFrequency();

        assertEquals(6, wordStat.getFrequency()); // Частота увеличилась на 1
    }
}