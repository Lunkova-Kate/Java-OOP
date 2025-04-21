package bombSquad.model;

public class GameRules {
    //нельзя чтобы игрок проиграл после первого клика!

    public boolean checkWinCondition(GameBoard board) {
        return board.isGameWon();
    }

}

/*GameRules.java
Поля :
firstClickSafe: Безопасен ли первый клик.
Методы :
checkWinCondition(board): Проверяет условие победы.
checkLoseCondition(cell): Проверяет условие проигрыша.

*/