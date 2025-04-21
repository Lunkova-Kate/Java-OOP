package bombSquad.controller;


import bombSquad.files.FileValidator;
import bombSquad.files.GameFileLoader;
import bombSquad.model.GameBoard;
import bombSquad.model.GameSaveManager;
import bombSquad.model.GameTimer;
import bombSquad.model.Score;
import bombSquad.view.TerminalView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TerminalController {
    private GameLogicController logicController;
    private final TerminalView terminalView;
    private final Scanner scanner;

    public TerminalController() {
        this.scanner = new Scanner(System.in);
        this.terminalView = new TerminalView();
    }

    public void startGame() {
        terminalView.displayWelcome();
        System.out.print("Введите путь к файлу с расстановкой бомб (или нажмите Enter для стандартной игры): ");
        String filePath = scanner.nextLine().trim();

        GameBoard board;
        if (!filePath.isEmpty()) {
            if (!FileValidator.isValidFile(filePath)) {
                terminalView.displayFileError("Файл не существует или не доступен для чтения.");
                board = createDefaultBoard();
            } else {
                try {
                    board = GameFileLoader.loadGameFromFile(filePath);
                } catch (Exception e) {
                    terminalView.displayFileError(e.getMessage());
                    board = createDefaultBoard();
                }
            }
        } else {
            board = createDefaultBoard();
        }

        logicController = new GameLogicController(board.getWidth(), board.getHeight(), board.getTotalBombs());
        logicController.getBoard().reset(); // Сбрасываем доску перед началом игры

        boolean running = true;
        while (running) {
            terminalView.displayBoard(logicController.getBoard(), false);
            terminalView.displayHelp();

            System.out.print("Введите команду: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            switch (parts[0].toLowerCase()) {
                case "retry", "r" -> resetGame();
                case "view", "v" -> showScores();
                case "exit", "e" -> running = false;
                case "help", "h" -> terminalView.displayHelp();
                default -> processCommand(parts);
            }
        }
        scanner.close();
    }


    private GameBoard createDefaultBoard() {
        return new GameBoard(9, 9, 10);
    }

    private void processCommand(String[] parts) {
        if (parts.length < 3) {
            terminalView.displayFileError("Неверный формат команды.");
            return;
        }
        int x, y;
        try {
            x = Integer.parseInt(parts[0]);
            y = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            terminalView.displayFileError("Координаты должны быть числами.");
            return;
        }

        String action = parts[2].toLowerCase();
        switch (action) {
            case "open", "o" -> handleOpenCommand(x, y);
            case "flag", "f" -> handleFlagCommand(x, y);
            default -> terminalView.displayFileError("Неизвестная команда.");
        }
    }

    private void handleOpenCommand(int x, int y) {
        if (logicController.isFirstClick()) {
            logicController.handleFirstClick(x, y);
        }
        boolean isBomb = logicController.openCell(x, y);

        if (isBomb) {
            terminalView.displayGameOver(false, logicController.getTimer());
        } else if (logicController.isGameWon()) {
            terminalView.displayGameOver(true, logicController.getTimer());
            saveResult(logicController.getTimer());
        }
    }

    private void handleFlagCommand(int x, int y) {
        logicController.toggleFlag(x, y);
    }

    private void resetGame() {
        logicController.resetGame();
        System.out.println("Игра перезапущена!");
    }

    private void showScores() {
        GameSaveManager saveManager = new GameSaveManager();
        try {
            List<Score> scores = saveManager.loadScores();

            terminalView.displayScores(scores);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке таблицы рекордов: " + e.getMessage());
        }
    }

    private void saveResult(GameTimer timer) {
        System.out.println("Поздравляем! Вы победили!");
        System.out.print("Введите ваше имя для таблицы рекордов: ");
        String playerName = scanner.nextLine().trim();

        if (playerName.isEmpty()) {
            playerName = "Anonymous";
        }

        GameBoard board = logicController.getBoard();
        int timeInSeconds = (int) (timer.getElapsedTime() / 1000);
        Score score = new Score(playerName, timeInSeconds, board.getWidth(), board.getHeight(), board.getTotalBombs());

        try {
            GameSaveManager.saveToFile(score, GameSaveManager.SCORES_FILE);
            System.out.println("Результат успешно сохранен!");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении результата: " + e.getMessage());
        }
    }
}
