import org.example.Main;
import org.example.calculatorSettings.CalculatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;





import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @TempDir
    Path tempDir; // Временная директория для тестовых файлов

    @Test
    void testFileModeTrue() throws IOException, CalculatorException {
        // Создаем временный файл с тестовыми командами
        File tempFile = tempDir.resolve("testCommands.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("PUSH 10\n");
            writer.write("PUSH 20\n");
            writer.write("ADD\n");
            writer.write("PRINT\n");
        }

        // Перехватываем вывод в консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Запускаем программу в режиме файла
        Main.main(new String[]{tempFile.getAbsolutePath()});

        // Возвращаем стандартный вывод
        System.setOut(originalOut);

        // Проверяем вывод программы
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("30.0"), "Expected output to contain '30.0', but got: " + consoleOutput);
    }


    @Test
    void testFileModeWithInvalidCommandsEquelsWithTrue() throws IOException {
        // Создаем временный файл с некорректными командами
        File tempFile = tempDir.resolve("invalidCommands.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("PUSH 10\n");
            writer.write("ADD\n"); // Недостаточно элементов в стеке
        }

        Exception exception = assertThrows(RuntimeException.class, () -> {
            Main.main(new String[]{tempFile.getAbsolutePath()});
        });

        assertTrue(exception.getCause() instanceof CalculatorException, "Ожидается CalculatorException как причина");
        assertEquals("Not enough elements in stack for ADD command", exception.getCause().getMessage());
    }

    @Test
    void testInteractiveModeTrue() throws CalculatorException {
        // Подготавливаем входные данные, которые будут симулировать ввод пользователя
        String input = "PUSH 10\nPUSH 20\nADD\nPRINT\nEXIT\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Перехватываем вывод в консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Запускаем основной метод, который будет использовать наш симулированный ввод
        Main.main(new String[]{});

        // Возвращаем стандартный ввод и вывод
        System.setIn(System.in);
        System.setOut(originalOut);

        // Проверяем вывод программы
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("30.0"), "Expected output to contain '30.0', but got: " + consoleOutput);
    }

}