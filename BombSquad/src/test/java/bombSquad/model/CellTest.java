package bombSquad.model;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testInitialStateEqualsAndFalse() {
        Cell cell = new Cell(0, 0);
        assertEquals(Cell.CellState.CLOSED, cell.getState());
        assertFalse(cell.isBomb());
        assertEquals(0, cell.getBombsAround());
    }

    @Test
    void testToggleFlagEquals() {
        Cell cell = new Cell(0, 0);

        cell.toggleFlag();
        assertEquals(Cell.CellState.FLAGGED, cell.getState());

        cell.toggleFlag();
        assertEquals(Cell.CellState.QUESTIONED, cell.getState());

        cell.toggleFlag();
        assertEquals(Cell.CellState.CLOSED, cell.getState());
    }

    @Test
    void testOpenCellFalseAndEquals() {
        Cell cell = new Cell(0, 0);

        boolean isBomb = cell.open();
        assertEquals(Cell.CellState.OPENED, cell.getState());
        assertFalse(isBomb);

        assertFalse(cell.open());
    }

    @Test
    void testResetFalseAndEquals() {
        Cell cell = new Cell(0, 0);
        cell.setBomb(true);
        cell.setBombsAround(5);
        cell.setState(Cell.CellState.OPENED);

        cell.reset();

        assertEquals(Cell.CellState.CLOSED, cell.getState());
        assertFalse(cell.isBomb());
        assertEquals(0, cell.getBombsAround());
    }
}