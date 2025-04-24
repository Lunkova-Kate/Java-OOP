package bombSquad.controller;


import bombSquad.model.Cell;
import bombSquad.model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicControllerTest {

    private GameLogicController logicController;

    @BeforeEach
    void setUp() {
        logicController = new GameLogicController(5, 5, 5);
    }

    @Test
    void testHandleFirstClickFalse() {
        assertTrue(logicController.isFirstClick());
        logicController.handleFirstClick(2, 2);
        assertFalse(logicController.isFirstClick());
    }

    @Test
    void testOpenCellBombTrue() {
        logicController.handleFirstClick(2, 2);

        // Ищем клетку с бомбой
        GameBoard board = logicController.getBoard();
        int bombX = -1, bombY = -1;
        outerLoop:
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getCells()[x][y].isBomb()) {
                    bombX = x;
                    bombY = y;
                    break outerLoop;
                }
            }
        }

        // Открываем клетку с бомбой
        boolean isBomb = logicController.openCell(bombX, bombY);
        assertTrue(isBomb);
        assertTrue(logicController.isGameOver());
    }

    @Test
    void testOpenCellEmptyFalse() {
        logicController.handleFirstClick(2, 2);

        // Ищем пустую клетку
        GameBoard board = logicController.getBoard();
        int emptyX = -1, emptyY = -1;
        outerLoop:
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (!board.getCells()[x][y].isBomb()) {
                    emptyX = x;
                    emptyY = y;
                    break outerLoop;
                }
            }
        }

        // Открываем пустую клетку
        boolean isBomb = logicController.openCell(emptyX, emptyY);
        assertFalse(isBomb);
        assertFalse(logicController.isGameOver());
    }

    @Test
    void testToggleFlagEquals() {
        logicController.handleFirstClick(2, 2);

        // Переключаем флаг
        logicController.toggleFlag(0, 0);
        Cell cell = logicController.getBoard().getCells()[0][0];
        assertEquals(Cell.CellState.FLAGGED, cell.getState());

        // Снова переключаем флаг
        logicController.toggleFlag(0, 0);
        assertEquals(Cell.CellState.QUESTIONED, cell.getState());

        // Еще раз переключаем флаг
        logicController.toggleFlag(0, 0);
        assertEquals(Cell.CellState.CLOSED, cell.getState());
    }

    @Test
    void testResetGameEqualsAndFalseAndTrue() {
        logicController.handleFirstClick(2, 2);
        logicController.openCell(0, 0);
        logicController.toggleFlag(1, 1);

        // Сбрасываем игру
        logicController.resetGame();

        // Проверяем состояние после сброса
        GameBoard board = logicController.getBoard();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCells()[x][y];
                assertEquals(Cell.CellState.CLOSED, cell.getState());
                assertFalse(cell.isBomb());
            }
        }
        assertTrue(logicController.isFirstClick());
        assertFalse(logicController.isGameOver());
        assertFalse(logicController.isGameWon());
    }
}