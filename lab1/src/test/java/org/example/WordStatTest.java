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
        WordStat wordStat2 = new WordStat("hello", 5); // Такое же слово, как у wordStat1
        WordStat wordStat3 = new WordStat("world", 3); // Другое слово
        WordStat wordStat4 = new WordStat("hello", 10); // Такое же слово, но другая частота

        assertTrue(wordStat1.equals(wordStat1));

        // Проверяем, что объекты с одинаковыми словами равны
        assertTrue(wordStat1.equals(wordStat2));
        assertTrue(wordStat2.equals(wordStat1));

        // Проверяем, что объекты с разными словами не равны
        assertFalse(wordStat1.equals(wordStat3));
        assertFalse(wordStat3.equals(wordStat1));
        // Проверяем, что объекты с одинаковыми словами, но разной частотой, равны
        assertTrue(wordStat1.equals(wordStat4)); // Частота не учитывается в equals
        assertTrue(wordStat4.equals(wordStat1));
        // Проверяем, что объект не равен null
        assertFalse(wordStat1.equals(null));
        // Проверяем, что объект не равен объекту другого класса
        assertFalse(wordStat1.equals(new Object()));
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

    @Test
    void testIncrementFrequencyFromZero() {
        WordStat wordStat = new WordStat("hello", 0);

        wordStat.incrementFrequency();

        assertEquals(1, wordStat.getFrequency()); // Частота увеличилась с 0 до 1
    }

}