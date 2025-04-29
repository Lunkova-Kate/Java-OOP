package bombSquad.files;


import bombSquad.model.GameBoard;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class GameFileLoaderTest {

    @Test
    void testLoadGameFromFileEquals() throws IOException {
        // Создаем временный файл с данными игры
        File tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("#F 9/9\n10\n"); // Ширина: 9, Высота: 9, Бомбы: 10
        }

        // Загружаем игру из файла
        GameBoard board = GameFileLoader.loadGameFromFile(tempFile.getAbsolutePath());

        // Проверяем размеры и количество бомб
        assertEquals(9, board.getWidth());
        assertEquals(9, board.getHeight());
        assertEquals(10, board.getTotalBombs());

        // Удаляем временный файл
        tempFile.delete();
    }

    @Test
    void testInvalidFileFormatTrue() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("INVALID_CONTENT");
        }

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GameFileLoader.loadGameFromFile(tempFile.getAbsolutePath());
        });
        assertTrue(exception.getMessage().contains("Неверный формат файла"));
    }
}