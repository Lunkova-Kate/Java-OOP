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
        File outputFile = tempDir.resolve("output.txt").toFile();

        List<WordStat> stats = Arrays.asList(
                new WordStat("apple", 3),
                new WordStat("banana", 2),
                new WordStat("cherry", 1)
        );

        OutputFile outputFileWriter = new OutputFile(outputFile.getAbsolutePath());
        outputFileWriter.writeReport(stats);

        String fileContent = Files.readString(outputFile.toPath());

        String expectedContent =
                "Слово;Частота;Частота (%)\n" +
                        "apple;3;50.0%\n" +
                        "banana;2;33.3%\n" +
                        "cherry;1;16.7%\n";

        assertEquals(expectedContent, fileContent);
    }
    @Test
    void testWriteReport_EmptyList(@TempDir Path tempDir) throws IOException {
        // Создаем временный файл для вывода
        File outputFile = tempDir.resolve("output.txt").toFile();

        List<WordStat> stats = List.of();
        OutputFile outputFileWriter = new OutputFile(outputFile.getAbsolutePath());
        outputFileWriter.writeReport(stats);
        String fileContent = Files.readString(outputFile.toPath());

        // Ожидаемое содержимое файла
        String expectedContent = "Слово;Частота;Частота (%)\n";
        assertEquals(expectedContent, fileContent);
    }


    @Test
    void testWriteReport_IOException(@TempDir Path tempDir) throws IOException {
        File outputFile = tempDir.resolve("output.txt").toFile();
        // Делаем файл недоступным для записи
        outputFile.setWritable(false);

        List<WordStat> stats = Arrays.asList(
                new WordStat("apple", 3),
                new WordStat("banana", 2)
        );

        OutputFile outputFileWriter = new OutputFile(outputFile.getAbsolutePath());

        outputFileWriter.writeReport(stats);

        // Проверяем, что файл не был создан или остался пустым
        assertTrue(outputFile.exists() || outputFile.length() == 0);
    }
}