package bombSquad.model;

public class GameRules {
    //нельзя чтобы игрок проиграл после первого клика!
    private boolean firstClickSafe;

    public boolean checkWinCondition(GameBoard board) {
        return board.isGameWon();
    }

    public boolean checkLoseCondition(Cell cell) {
        return cell.isBomb() && cell.getState() == Cell.CellState.OPENED;
    }

    public boolean isGameFinished(GameBoard board, Cell lastOpenedCell) {
        return checkLoseCondition(lastOpenedCell) || checkWinCondition(board);
    }

}

/*GameRules.java
Поля :
firstClickSafe: Безопасен ли первый клик.
Методы :
checkWinCondition(board): Проверяет условие победы.
checkLoseCondition(cell): Проверяет условие проигрыша.
isGameFinished(board, lastOpenedCell): Проверяет, завершена ли игра.
Геттеры и сеттеры для полей.*/