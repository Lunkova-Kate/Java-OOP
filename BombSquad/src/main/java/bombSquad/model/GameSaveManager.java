package bombSquad.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {
    public static final String SCORES_FILE = "scores.txt";


    public static void saveToFile(Score score, String filename) throws IOException {
        File file = new File(SCORES_FILE);
        System.out.println("Saving to: " + file.getAbsolutePath());
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s,%d,%d,%d,%d%n",
                    score.getPlayerName(), score.getTime(), score.getWidth(), score.getHeight(), score.getBombs()));
            writer.flush(); //без заполнения полностью буфера
        }
    }

    public List<Score> loadScores() throws IOException {
        File file = new File(SCORES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Score> scores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    try {
                        String playerName = parts[0].trim();
                        long time = Long.parseLong(parts[1].trim());
                        int width = Integer.parseInt(parts[2].trim());
                        int height = Integer.parseInt(parts[3].trim());
                        int bombs = Integer.parseInt(parts[4].trim());
                        scores.add(new Score(playerName, time, width, height, bombs));
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка чтения строки: " + line);
                    }
                }
            }
            return scores;
        }
    }


}
/*GameSaveManager.java
Методы :
saveScore -  сохраняет для таблички
saveToFile. - для файлика
loadScores -  загрузка из файлика
writeScores - вспомогательный метод

*/