package bombSquad.model;

public class Cell {
    public void setState(CellState cellState) {
        this.state = cellState;
    }

    public enum CellState {
        CLOSED, OPENED, FLAGGED, QUESTIONED
    }

    private CellState state;
    private boolean isBomb;
    private int bombsAround;
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = CellState.CLOSED;
        this.isBomb = false;
        this.bombsAround = 0;
    }

    public void toggleFlag() {
        switch (state) {
            case CLOSED:
                state = CellState.FLAGGED;
                break;
            case FLAGGED:
                state = CellState.QUESTIONED;
                break;
            case QUESTIONED:
                state = CellState.CLOSED;
                break;
            case OPENED:
                break;
        }
    }

    public boolean open() {
        if (state == CellState.OPENED || state == CellState.FLAGGED) {
            return false;
        }

        state = CellState.OPENED;
        return isBomb;
    }

    public void reset() {
        state = CellState.CLOSED;
        isBomb = false;
        bombsAround = 0;
    }


    public CellState getState() {
        return state;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public int getBombsAround() {
        return bombsAround;
    }

    public void setBombsAround(int bombsAround) {
        this.bombsAround = bombsAround;
    }


}
