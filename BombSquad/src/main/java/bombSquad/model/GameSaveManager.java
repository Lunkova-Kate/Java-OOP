package bombSquad.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameSaveManager {
    public static final String SCORES_FILE = "scores.txt";

    private static final int MAX_SCORES = 10;

    public void saveScore(Score newScore) throws IOException {
        List<Score> scores = loadScores();
        scores.add(newScore);
        scores.sort(this::compareScores);
        scores = scores.stream()
                .collect(Collectors.toMap(
                        s -> s.getPlayerName(), // Ключ - имя игрока
                        s -> s,                // Значение - результат
                        (existing, replacement) -> existing // Если дубликат, сохраняем существующий
                ))
                .values()
                .stream()
                .sorted(this::compareScores) // Снова сортируем после удаления дубликатов
                .collect(Collectors.toList());

        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        writeScores(scores);
    }

    private int compareScores(Score s1, Score s2) {
        if (s1.getBombs() != s2.getBombs()) {
            return Integer.compare(s2.getBombs(), s1.getBombs());
        }
        if (s1.getTime() != s2.getTime()) {
            return Long.compare(s1.getTime(), s2.getTime());
        }
        return s1.getPlayerName().compareTo(s2.getPlayerName());
    }

    private void writeScores(List<Score> scores) throws IOException {
        File file = new File(SCORES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Score score : scores) {
                writer.write(String.format("%s,%d,%d,%d,%d%n",
                        score.getPlayerName(), score.getTime(),
                        score.getWidth(), score.getHeight(), score.getBombs()));
            }
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
            // сразу сортируем при загрузке
            scores.sort(this::compareScores);
            return scores;
        }
    }


}