package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreamParserTest {

    @Test
    void testStreamParser_ValidArgs() {

        String[] args = {"input.txt", "output.txt"};

        StreamParser parser = new StreamParser(args);

        assertEquals("input.txt", parser.getInputFile());
        assertEquals("output.txt", parser.getOutputFile());
    }

    @Test
    void testStreamParser_InsufficientArgs() {

        String[] args = {"input.txt"};

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new StreamParser(args)
        );
        assertEquals("Использование: ./program <inputFile> <outputFile>", exception.getMessage());
    }

    @Test
    void testStreamParser_NoArgs() {

        String[] args = {};

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new StreamParser(args)
        );
        assertEquals("Использование: ./program <inputFile> <outputFile>", exception.getMessage());
    }
}