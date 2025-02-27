package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void testGenerateReport_Success(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("input.txt").toFile();
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("test data");
        }

        File outputFile = tempDir.resolve("output.txt").toFile();

        Controller controller = new Controller(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());

        controller.generateReport();

        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0); // Проверяем, что файл не пустой
    }

    @Test
    void testGenerateReport_FileNotFound(@TempDir Path tempDir) {
        //  несуществующий входной файл
        String nonExistentInputFile = tempDir.resolve("nonexistent.txt").toString();
        File outputFile = tempDir.resolve("output.txt").toFile();

        Controller controller = new Controller(nonExistentInputFile, outputFile.getAbsolutePath());

        controller.generateReport();

        // Проверяем, что выходной файл не был создан
        assertFalse(outputFile.exists());
    }

    @Test
    void testGenerateReport_IOException(@TempDir Path tempDir) throws IOException {
        // Создаем временный входной файл, который нельзя прочитать
        File inputFile = tempDir.resolve("input.txt").toFile();
        inputFile.createNewFile(); // Создаем пустой файл
        inputFile.setReadable(false); // Делаем файл недоступным для чтения

        // Создаем временный выходной файл
        File outputFile = tempDir.resolve("output.txt").toFile();

        Controller controller = new Controller(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());

        controller.generateReport();

        // Проверяем, что выходной файл не был создан
        assertFalse(outputFile.exists());
    }
}