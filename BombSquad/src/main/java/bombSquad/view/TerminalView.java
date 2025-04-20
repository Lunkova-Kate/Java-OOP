package bombSquad.view;

import bombSquad.model.Cell;
import bombSquad.model.GameBoard;
import bombSquad.model.GameTimer;
import bombSquad.model.Score;

import java.util.List;

public class TerminalView {


        public void displayBoard(GameBoard board, boolean showBombs) {
            System.out.println("\n   " + getColumnHeaders(board.getWidth()));
            System.out.println("   " + "-".repeat(board.getWidth() * 3 + 1));

            for (int y = 0; y < board.getHeight(); y++) {
                System.out.print(y + " |");
                for (int x = 0; x < board.getWidth(); x++) {
                    Cell cell = board.getCells()[x][y];
                    System.out.print(" " + getCellSymbol(cell, showBombs) + " ");
                }
                System.out.println("|");
            }
            System.out.println("   " + "-".repeat(board.getWidth() * 3 + 1));

            System.out.println("Flags left: " + board.getRemainingFlags());
        }
//циферки сверху
    private String getColumnHeaders(int width) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            sb.append(String.format("%2d ", i));
        }
        return sb.toString();
    }

    private String getCellSymbol(Cell cell, boolean showBombs) {
        switch (cell.getState()) {
            case OPENED:
                if (cell.isBomb()) {
                    return "o";
                } else {
                    return cell.getBombsAround() > 0 ?
                            String.valueOf(cell.getBombsAround()) : " ";
                }
            case FLAGGED:
                return "F";
            case QUESTIONED:
                return "?";
            case CLOSED:
                return showBombs && cell.isBomb() ? "o" : "[]";
            default:
                return " ";
        }
    }

    public void displayScores(List<Score> scores) {
        if (scores.isEmpty()) {
            System.out.println("No scores recorded yet.");
            return;
        }

        System.out.println("\nHigh Scores:");
        System.out.println("-------------------------------------------------");
        System.out.println("| Name       | Time  | Size   | Bombs | Difficulty |");
        System.out.println("-------------------------------------------------");

        for (Score score : scores) {
            System.out.printf("| %-10s | %5s | %dx%-2d | %5d | %-10s |\n",
                    score.getPlayerName(),
                    score.getFormattedTime(),
                    score.getWidth(),
                    score.getHeight(),
                    score.getBombs(),
                    score.getDifficulty());
        }
        System.out.println("-------------------------------------------------");
    }

    public void displayWelcome() {
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Enter the path to the bomb locations file:");
    }

    public void displayGameOver(boolean won, GameTimer timer) {
        if (won) {
            System.out.println("\nCongratulations! You won!");
        } else {
            System.out.println("\nGame Over! You hit a bomb!");
        }
        System.out.println("Time: " + timer.getFormattedTime());
        System.out.println("Enter 'retry' to play again or 'exit' to quit");
    }

    public void displayHelp() {
        System.out.println("\nCommands:");
        System.out.println("<x> <y> open/o - Open a cell");
        System.out.println("<x> <y> flag/f - Place a flag, second f place ?, third cells is close");
        System.out.println("<x> <y> question/q - Place a question mark");
        System.out.println("retry - Restart the game");
        System.out.println("view result - Show high scores");
        System.out.println("exit - Quit the game");
        System.out.println("help - Show this help message");
    }

    public void displayFileError(String message) {
        System.out.println("Error reading file: " + message);
    }

    public void promptForPlayerName() {
        System.out.println("\nYou won! Enter your name for the high score table:");
    }
}
/*TerminalView.java
Методы :
displayBoard(board, showBombs): Отображает игровое поле в терминале.
displayGameOver(won, timer): Показывает сообщение о конце игры.
displayHelp(): Показывает справку.
displayScores(scores): Отображает таблицу рекордов.
Другие методы для вывода информации в терминал.
*/