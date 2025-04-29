package bombSquad.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void testGetFormattedTimeEquals() {
        Score score = new Score("Player1", 125, 5, 5, 5);
        assertEquals("02:05", score.getFormattedTime());
    }

    @Test
    void testGetDifficultyEquals() {
        Score easy = new Score("Player1", 100, 10, 10, 10);
        Score medium = new Score("Player2", 100, 10, 10, 15);
        Score hard = new Score("Player3", 100, 10, 10, 20);

        assertEquals("Легко", easy.getDifficulty());
        assertEquals("Средне", medium.getDifficulty());
        assertEquals("Сложно", hard.getDifficulty());
    }
}