package bombSquad.model;

import java.util.Random;

public class GameBoard {

    private final  Cell[][] cells;
    private final int width;
    private final int height;
    private final int totalBombs;
    private int flagsPlaced;
    boolean firstClick = true;

    public GameBoard(int width, int height, int totalBombs) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Ширина и высота поля должны быть положительными числами.");
        }
        if (totalBombs <= 0) {
            throw new IllegalArgumentException("Количество бомб должно быть больше 0.");
        }
        int totalCells = width * height;
        if (totalBombs >= totalCells) {
            throw new IllegalArgumentException("Количество бомб не может быть больше или равно общему количеству клеток (" + totalCells + ").");
        }

        this.width = width;
        this.height = height;
        this.totalBombs = totalBombs;
        this.flagsPlaced = 0;
        this.cells = new Cell[width][height];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public void placeBombs(int kx, int ky){
        Random random = new Random();
        int countBombsPlaced = 0;
        while( countBombsPlaced< totalBombs){
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if ((x == kx && y == ky) ||
                Math.abs(x - kx)<= 1 && Math.abs(y - ky)<=1){
                continue;
            }

            if (!cells[x][y].isBomb()) {
                cells[x][y].setBomb(true);
                countBombsPlaced++;
            }
        }
        calculateBombsAround();
        firstClick = false;
    }

 public void calculateBombsAround() {
     for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
             if (!cells[x][y].isBomb()) {
                 int count = countAdjacentBombs(x, y);
                 cells[x][y].setBombsAround(count);
             }
         }
     }
 }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private int countAdjacentBombs(int x, int y) {
        int count = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int nx = x + dx;
                int ny = y + dy;

                if (isValidPosition(nx, ny) && cells[nx][ny].isBomb()) {
                    count++;
                }
            }
        }

        return count;
    }


    public void toggleFlag(int x, int y) {
        if (!isValidPosition(x, y)) return;

        Cell cell = cells[x][y];
        Cell.CellState currentState = cell.getState();
        if (currentState == Cell.CellState.OPENED) return;
        boolean wasFlagged = (currentState == Cell.CellState.FLAGGED);
        cell.toggleFlag();

        if (wasFlagged && cell.getState() != Cell.CellState.FLAGGED) {
            flagsPlaced--;
        } else if (currentState == Cell.CellState.CLOSED && cell.getState() == Cell.CellState.FLAGGED) {
            if (flagsPlaced < totalBombs) {
                flagsPlaced++;
            } else {
                cell.toggleFlag();
            }
        }

    }


    public boolean isGameWon() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = cells[x][y];
                if (!cell.isBomb() && cell.getState() != Cell.CellState.OPENED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void reset() {
        firstClick = true;
        flagsPlaced = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y].reset();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTotalBombs() {
        return totalBombs;
    }

    public int getRemainingFlags() {
        return totalBombs - flagsPlaced;
    }

}
