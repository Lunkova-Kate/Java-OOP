package bombSquad.files;

import bombSquad.model.GameBoard;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GameFileLoader {
    public static GameBoard loadGameFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            if (header == null || !header.startsWith("#F")) {
                throw new IllegalArgumentException("Неверный формат файла.");
            }
            String[] dimensions = header.split(" ")[1].split("/");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            GameBoard board = new GameBoard(width, height, 0);

            Set<String> bombPositions = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] coords = line.split(" ");
                if (coords.length != 2) {
                    throw new IllegalArgumentException("Неверный формат координат бомбы: " + line);
                }
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                if (!board.isValidPosition(x, y)) {
                    throw new IllegalArgumentException("Координаты бомбы вне поля: " + line);
                }
                bombPositions.add(x + "," + y);
            }

            int totalBombs = bombPositions.size();
            board = new GameBoard(width, height, totalBombs);
            for (String pos : bombPositions) {
                String[] coords = pos.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                board.getCells()[x][y].setBomb(true);
            }

            board.calculateBombsAround();

            return board;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка при чтении числовых данных: " + e.getMessage());
        }
    }
}