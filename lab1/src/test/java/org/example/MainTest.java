package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @TempDir
    Path tempDir;

    @Test
    void testMain_Success() throws IOException {
        File inputFile = tempDir.resolve("input.txt").toFile();
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("hello world hello");
        }
        File outputFile = tempDir.resolve("output.txt").toFile();

        String[] args = {inputFile.getAbsolutePath(), outputFile.getAbsolutePath()};

        Main.main(args);
        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    void testMain_InsufficientArgs() {
        String[] args = {"input.txt"};

        Main.main(args);

    }

    @Test
    void testMain_FileNotFound() {
        String[] args = {"nonexistent.txt", "output.txt"};

        Main.main(args);

    }
}