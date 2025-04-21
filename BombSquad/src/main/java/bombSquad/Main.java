package bombSquad;

import bombSquad.controller.TerminalController;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--terminal")) {
            // терминальный режим
            TerminalController terminalController = new TerminalController();
            terminalController.startGame();
        } else {
            //графическй режим
            Application.launch(MainApp.class, args);
        }
    }
}