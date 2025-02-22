package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @Test
    void testAnalyze_SingleWord() throws IOException {
        String input = "hello";
        ReportGenerator generator = new ReportGenerator();

        generator.analyze(new StringReader(input));

        List<WordStat> stats = generator.getSortedWordStats();
        assertEquals(1, stats.size());
        assertEquals("hello", stats.get(0).getWord());
        assertEquals(1, stats.get(0).getFrequency());
    }

    @Test
    void testAnalyze_MultipleWords() throws IOException {
        String input = "hello world hello";
        ReportGenerator generator = new ReportGenerator();

        generator.analyze(new StringReader(input));

        List<WordStat> stats = generator.getSortedWordStats();
        assertEquals(2, stats.size());
        assertEquals("hello", stats.get(0).getWord());
        assertEquals(2, stats.get(0).getFrequency());
        assertEquals("world", stats.get(1).getWord());
        assertEquals(1, stats.get(1).getFrequency());
    }

    @Test
    void testAnalyze_EmptyInput() throws IOException {
        String input = "";
        ReportGenerator generator = new ReportGenerator();

        generator.analyze(new StringReader(input));

        List<WordStat> stats = generator.getSortedWordStats();
        assertTrue(stats.isEmpty());
    }

    @Test
    void testAnalyze_IgnoreNonLetters() throws IOException {
        String input = "hello, world! 123";
        ReportGenerator generator = new ReportGenerator();

        generator.analyze(new StringReader(input));

        List<WordStat> stats = generator.getSortedWordStats();
        assertEquals(3, stats.size());
        assertEquals("123", stats.get(0).getWord());
        assertEquals("world", stats.get(1).getWord());
        assertEquals("hello", stats.get(2).getWord());
    }

    @Test
    void testGetSortedWordStats_Sorting() throws IOException {
        String input = "apple banana apple cherry banana apple";
        ReportGenerator generator = new ReportGenerator();

        generator.analyze(new StringReader(input));

        List<WordStat> stats = generator.getSortedWordStats();
        assertEquals(3, stats.size());
        assertEquals("apple", stats.get(0).getWord());
        assertEquals(3, stats.get(0).getFrequency());
        assertEquals("banana", stats.get(1).getWord());
        assertEquals(2, stats.get(1).getFrequency());
        assertEquals("cherry", stats.get(2).getWord());
        assertEquals(1, stats.get(2).getFrequency());
    }
}