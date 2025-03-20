package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @TempDir
    Path tempDir; // пусть будет ременная директория для тестовых файлов

    @Test
    void testFileMode() throws IOException, CalculatorException {
        // создаем временный файл с тестовыми командами
        File tempFile = tempDir.resolve("testCommands.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("PUSH 10\n");
            writer.write("PUSH 20\n");
            writer.write("ADD\n");
            writer.write("PRINT\n");
        }

        // перехватываем вывод в консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // запускаем программу в режиме файла
        Main.main(new String[]{tempFile.getAbsolutePath()});

        // Возвращаем стандартный вывод для других тестиков, чтобы ничего не сломалось..
        System.setOut(originalOut);

        // проверяем 
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("30.0"), "Expected output to contain '30.0', but got: " + consoleOutput);
    }

    @Test
    void testFileModeWithInvalidFile() throws CalculatorException {
        // перехватываем вывод в System.err
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errorStream));

        // запускаем программы с несуществующим файлом
        Main.main(new String[]{"nonexistent_file.txt"});

        // возвращаем стандартный вывод ошибок
        System.setErr(originalErr);

     
        String errorOutput = errorStream.toString().trim();
        assertTrue(errorOutput.contains("Error reading file"), "Expected error message about reading file, but got: " + errorOutput);
    }

    @Test
    void testFileModeWithInvalidCommands() throws IOException {
        // создаем файл с некорректными командами
        File tempFile = tempDir.resolve("invalidCommands.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("PUSH 10\n");
            writer.write("ADD\n"); // Недостаточно элементов в стеке
        }

        // проверяем, что выбрасывается исключение RuntimeException, вызванное CalculatorException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Main.main(new String[]{tempFile.getAbsolutePath()});
        });

        // проверяем причину исключения
        assertTrue(exception.getCause() instanceof CalculatorException, "Ожидается CalculatorException как причина");
        assertEquals("Not enough elements in stack for ADD command", exception.getCause().getMessage());
    }
}