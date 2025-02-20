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
        // Создаем временный входной файл с тестовыми данными
        File inputFile = tempDir.resolve("input.txt").toFile();
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("hello world hello");
        }

        // Создаем временный выходной файл
        File outputFile = tempDir.resolve("output.txt").toFile();

        // Аргументы командной строки
        String[] args = {inputFile.getAbsolutePath(), outputFile.getAbsolutePath()};

        Main.main(args);

        // Проверяем, что выходной файл создан и не пуст
        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    void testMain_InsufficientArgs() {
        // Аргументы командной строки (недостаточно аргументов)
        String[] args = {"input.txt"};

        Main.main(args);

    }

    @Test
    void testMain_FileNotFound() {
        // Аргументы командной строки (несуществующий входной файл)
        String[] args = {"nonexistent.txt", "output.txt"};

        Main.main(args);

    }
}