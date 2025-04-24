package bombSquad.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {

        gameBoard = new GameBoard(5, 5, 5);
    }

    @Test
    void testInitialization() {
        assertEquals(5, gameBoard.getWidth());
        assertEquals(5, gameBoard.getHeight());
        assertEquals(5, gameBoard.getTotalBombs());
        assertEquals(5, gameBoard.getRemainingFlags());
        assertNotNull(gameBoard.getCells());
    }

    @Test
    void testPlaceBombsFalseAndEquals() {
        int safeX = 2, safeY = 2;
        gameBoard.placeBombs(safeX, safeY);

        assertFalse(gameBoard.getCells()[safeX][safeY].isBomb());

        // Проверяем, что количество бомб соответствует ожидаемому
        int bombCount = 0;
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                if (gameBoard.getCells()[x][y].isBomb()) {
                    bombCount++;
                }
            }
        }
        assertEquals(5, bombCount);
    }

    @Test
    void testCalculateBombsAroundEqual() {
        int safeX = 1, safeY = 1;
        gameBoard.placeBombs(safeX, safeY);

        Cell[][] cells = gameBoard.getCells();
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                if (!cells[x][y].isBomb()) {
                    int expectedCount = countAdjacentBombsManually(x, y, cells);
                    assertEquals(expectedCount, cells[x][y].getBombsAround());
                }
            }
        }
    }

    private int countAdjacentBombsManually(int x, int y, Cell[][] cells) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < gameBoard.getWidth() && ny >= 0 && ny < gameBoard.getHeight()) {
                    if (cells[nx][ny].isBomb()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Test
    void testToggleFlagEquals() {
        int x = 0, y = 0;
        Cell cell = gameBoard.getCells()[x][y];

        assertEquals(Cell.CellState.CLOSED, cell.getState());

        gameBoard.toggleFlag(x, y);
        assertEquals(Cell.CellState.FLAGGED, cell.getState());
        assertEquals(4, gameBoard.getRemainingFlags());

        gameBoard.toggleFlag(x, y);
        assertEquals(Cell.CellState.QUESTIONED, cell.getState());
        assertEquals(5, gameBoard.getRemainingFlags());

        gameBoard.toggleFlag(x, y);
        assertEquals(Cell.CellState.CLOSED, cell.getState());
        assertEquals(5, gameBoard.getRemainingFlags());
    }

    @Test
    void testIsGameWonTrue() {
        gameBoard.placeBombs(0, 0);

        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                if (!gameBoard.getCells()[x][y].isBomb()) {
                    gameBoard.getCells()[x][y].setState(Cell.CellState.OPENED);
                }
            }
        }

        assertTrue(gameBoard.isGameWon());
    }

    @Test
    void testResetEqualsAndTrue() {
        gameBoard.placeBombs(0, 0);
        gameBoard.toggleFlag(0, 0);

        gameBoard.reset();

        // Проверяем, что игра сброшена
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                Cell cell = gameBoard.getCells()[x][y];
                assertFalse(cell.isBomb());
                assertEquals(Cell.CellState.CLOSED, cell.getState());
            }
        }
        assertEquals(5, gameBoard.getRemainingFlags());
        assertTrue(gameBoard.firstClick);
    }
}