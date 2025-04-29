package bombSquad.model;

public class GameRules {


    public boolean checkWinCondition(GameBoard board) {
        return board.isGameWon();
    }

}
