package bombSquad.controller;


import bombSquad.model.Cell;
import bombSquad.model.GameBoard;
import bombSquad.model.GameRules;
import bombSquad.model.GameTimer;

public class GameLogicController {
    private final GameBoard board;
    private final GameRules rules;
    private final GameTimer timer;
    private boolean gameOver;
    private boolean gameWon;
    private boolean firstClick;

    public GameLogicController(int width, int height, int totalBombs) {
        this.board = new GameBoard(width, height, totalBombs);
        this.rules = new GameRules();
        this.timer = new GameTimer();
        this.gameOver = false;
        this.gameWon = false;
        this.firstClick = true;
    }

    public void handleFirstClick(int x, int y) {
        if (firstClick) {
            board.placeBombs(x, y);
            timer.start();
            firstClick = false; // Первый клик выполнен
        }
    }

    public boolean openCell(int x, int y) {
        if (!board.isValidPosition(x, y) || gameOver || gameWon) {
            return false;
        }

        Cell cell = board.getCells()[x][y];

        if (cell.getState() == Cell.CellState.FLAGGED) {
            return false;
        }

        boolean isBomb = cell.open();
        if (isBomb) {
            gameOver = true;
            timer.pause();
            return true;
        }

        if (cell.getBombsAround() == 0) {
            openAdjacentCells(x, y);
        }

        gameWon = rules.checkWinCondition(board);
        if (gameWon) {
            timer.pause();
        }

        return false;
    }

    /**
     * Рекурсивно открывает соседние клетки.
     */
    private void openAdjacentCells(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx;
                int ny = y + dy;
                if (board.isValidPosition(nx, ny)) {
                    Cell neighbor = board.getCells()[nx][ny];
                    if (neighbor.getState() == Cell.CellState.CLOSED ||
                            neighbor.getState() == Cell.CellState.QUESTIONED) {
                        openCell(nx, ny);
                    }
                }
            }
        }
    }

    public void toggleFlag(int x, int y) {
        if (!board.isValidPosition(x, y) || gameOver || gameWon) return;
        board.toggleFlag(x, y);
    }

    public void resetGame() {
        board.reset();
        timer.reset();
        gameOver = false;
        gameWon = false;
        firstClick = true;
    }

    public GameBoard getBoard() {
        return board;
    }

    public GameTimer getTimer() {
        return timer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isFirstClick() {
        return firstClick;
    }
}