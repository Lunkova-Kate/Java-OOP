package bombSquad.files;

import bombSquad.model.GameBoard;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Random;

public class GameFileLoader {
    public static GameBoard loadGameFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            if (header == null || !header.startsWith("#F")) {
                throw new IllegalArgumentException("Неверный формат файла. Заголовок должен начинаться с '#F'.");
            }
            String[] dimensions = header.split(" ")[1].split("/");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);
            String bombLine = reader.readLine();
            if (bombLine == null || !bombLine.matches("\\d+")) {
                throw new IllegalArgumentException("Неверный формат количества бомб. Ожидалось целое число.");
            }
            int totalBombs = Integer.parseInt(bombLine.trim());

            GameBoard board = new GameBoard(width, height, totalBombs);

            placeBombsRandomly(board, totalBombs);

            return board;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка при чтении числовых данных: " + e.getMessage());
        }
    }


    private static void placeBombsRandomly(GameBoard board, int totalBombs) {
        Random random = new Random();
        int width = board.getWidth();
        int height = board.getHeight();
        int bombsPlaced = 0;

        while (bombsPlaced < totalBombs) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if (!board.getCells()[x][y].isBomb()) {
                board.getCells()[x][y].setBomb(true);
                bombsPlaced++;
            }
        }

        board.calculateBombsAround();
    }
}