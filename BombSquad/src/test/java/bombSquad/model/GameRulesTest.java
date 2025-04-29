package bombSquad.model;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameRulesTest {

    @Test
    void testCheckWinConditionTrue() {
        GameBoard board = new GameBoard(3, 3, 2);
        board.placeBombs(0, 0);

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (!board.getCells()[x][y].isBomb()) {
                    board.getCells()[x][y].setState(Cell.CellState.OPENED);
                }
            }
        }

        GameRules rules = new GameRules();
        assertTrue(rules.checkWinCondition(board));
    }
}