package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutputFileTest {

    @Test
    void testWriteReport(@TempDir Path tempDir) throws IOException {
        // Создаем временный файл для вывода
        File outputFile = tempDir.resolve("output.txt").toFile();

        // Создаем тестовые данные
        List<WordStat> stats = Arrays.asList(
                new WordStat("apple", 3),
                new WordStat("banana", 2),
                new WordStat("cherry", 1)
        );

        // Создаем экземпляр OutputFile
        OutputFile outputFileWriter = new OutputFile(outputFile.getAbsolutePath());

        // Вызываем метод, который тестируем
        outputFileWriter.writeReport(stats);

        // Читаем содержимое файла
        String fileContent = Files.readString(outputFile.toPath());

        // Ожидаемое содержимое файла
        String expectedContent =
                "Слово;Частота;Частота (%)\n" +
                        "apple;3;50.0%\n" +
                        "banana;2;33.333%\n" +
                        "cherry;1;16.667%\n";

        // Проверяем, что содержимое файла соответствует ожидаемому
        assertEquals(expectedContent, fileContent);
    }

    @Test
    void testWriteReport_EmptyList(@TempDir Path tempDir) throws IOException {
        // Создаем временный файл для вывода
        File outputFile = tempDir.resolve("output.txt").toFile();

        // Создаем пустой список данных
        List<WordStat> stats = List.of();

        // Создаем экземпляр OutputFile
        OutputFile outputFileWriter = new OutputFile(outputFile.getAbsolutePath());

        // Вызываем метод, который тестируем
        outputFileWriter.writeReport(stats);

        // Читаем содержимое файла
        String fileContent = Files.readString(outputFile.toPath());

        // Ожидаемое содержимое файла (только заголовок)
        String expectedContent = "Слово;Частота;Частота (%)\n";

        // Проверяем, что содержимое файла соответствует ожидаемому
        assertEquals(expectedContent, fileContent);
    }

    @Test
    void testWriteReport_IOException(@TempDir Path tempDir) {
        // Создаем временный файл, который нельзя записать
        File outputFile = tempDir.resolve("output.txt").toFile();
        outputFile.setWritable(false); // Делаем файл недоступным для записи

        // Создаем тестовые данные
        List<WordStat> stats = Arrays.asList(
                new WordStat("apple", 3),
                new WordStat("banana", 2)
        );

        // Создаем экземпляр OutputFile
        OutputFile outputFileWriter = new OutputFile(outputFile.getAbsolutePath());

        // Вызываем метод, который тестируем
        outputFileWriter.writeReport(stats);

        // Проверяем, что файл остался пустым (или не был создан)
        assertFalse(outputFile.exists() || outputFile.length() == 0);
    }
}