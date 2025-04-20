package bombSquad;


import bombSquad.controller.TerminalController;

public class Main {
    /**
     * Запускает игру в терминальном режиме.
     */
    public static void main(String[] args) {
        TerminalController terminalController = new TerminalController();
        terminalController.startGame();
    }
}