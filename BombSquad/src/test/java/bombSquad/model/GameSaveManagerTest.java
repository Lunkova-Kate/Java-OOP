package bombSquad.model;


import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameSaveManagerTest {

    private final GameSaveManager saveManager = new GameSaveManager();

    @Test
    void testSaveAndLoadScoresEquals() throws IOException {
        Path tempFile = Files.createTempFile("scores", ".txt");
        System.setProperty("user.dir", tempFile.getParent().toString());
        System.setProperty(GameSaveManager.SCORES_FILE, tempFile.toString());

        Score score1 = new Score("Player1", 100, 5, 5, 5);
        Score score2 = new Score("Player2", 50, 5, 5, 5);

        saveManager.saveScore(score1);
        saveManager.saveScore(score2);

        List<Score> scores = saveManager.loadScores();
        assertEquals(10, scores.size());
        assertEquals("Player2", scores.get(0).getPlayerName());


        Files.delete(tempFile);
    }

    @Test
    void testMaxScoresLimitEquals() throws IOException {
        Path tempFile = Files.createTempFile("scores", ".txt");
        System.setProperty("user.dir", tempFile.getParent().toString());
        System.setProperty(GameSaveManager.SCORES_FILE, tempFile.toString());

        for (int i = 0; i < 15; i++) {
            saveManager.saveScore(new Score("Player" + i, 100 - i, 5, 5, 5));
        }

        List<Score> scores = saveManager.loadScores();
        assertEquals(10, scores.size());
        assertEquals("Player2", scores.get(0).getPlayerName());
        assertEquals("Player6", scores.get(9).getPlayerName());

        Files.delete(tempFile);
    }
}