package bombSquad.controller;

import bombSquad.files.FileValidator;
import bombSquad.files.GameFileLoader;
import bombSquad.model.*;
import bombSquad.view.*;
import javafx.animation.AnimationTimer;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class GameUIController {
    private final Stage primaryStage;
    private GameLogicController logicController;
    private GameView gameView;
    private UIElements uiElements;
    private AnimationTimer timerAnimation;

    public GameUIController(Stage primaryStage, int width, int height, int totalBombs) {
        this.primaryStage = primaryStage;
        this.logicController = new GameLogicController(width, height, totalBombs);
        this.gameView = new GameView(logicController.getBoard());
        this.uiElements = new UIElements(logicController.getBoard(), logicController.getTimer());

        setupEventHandlers();
        startTimer();
        updateUI();
        showGameScene();
    }

    private void setupEventHandlers() {
        EventHandler<MouseEvent> leftClickHandler = event -> {
            try {
                ImageView clickedCell = (ImageView) event.getSource();
                int x = GridPane.getColumnIndex(clickedCell);
                int y = GridPane.getRowIndex(clickedCell);

                if (logicController.isFirstClick()) {
                    logicController.handleFirstClick(x, y);
                }

                boolean isBomb = logicController.openCell(x, y);
                if (isBomb) {
                    handleGameOver();
                } else if (logicController.isGameWon()) {
                    handleGameWon();
                }

                updateUI();
            } catch (Exception e) {
                System.err.println("Ошибка при обработке клика: " + e.getMessage());
            }
        };

        EventHandler<MouseEvent> rightClickHandler = event -> {
            try {
                ImageView clickedCell = (ImageView) event.getSource();
                int x = GridPane.getColumnIndex(clickedCell);
                int y = GridPane.getRowIndex(clickedCell);

                logicController.toggleFlag(x, y); // Переключение флага
                gameView.updateCell(x, y);
                updateUI();
            } catch (Exception e) {
                System.err.println("Ошибка при обработке клика: " + e.getMessage());
            }
        };

        gameView.setCellClickHandlers(leftClickHandler, rightClickHandler);

        uiElements.getSmileyButton().setOnAction(event -> resetGame());
    }


    private void startTimer() {
        timerAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateUI();
            }
        };
        timerAnimation.start();
    }

    private void updateUI() {
        GameBoard board = logicController.getBoard();

        uiElements.updateTimer();
        uiElements.updateBombCounter();

        if (logicController.isGameOver()) {
            uiElements.setSmileyState("lose");
        } else if (logicController.isGameWon()) {
            uiElements.setSmileyState("win");
        } else {
            uiElements.setSmileyState("normal");
        }

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                gameView.updateCell(x, y);
            }
        }
    }


    private void showGameScene() {
        VBox root = new VBox(uiElements, gameView);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleGameOver() {
        logicController.getTimer().pause();
        gameView.updateAllCells(); // Показываем все бомбы
        //чем-то надо стопнуть
        showWinLoseScreen(false);
    }


    private void handleGameWon() {
        logicController.getTimer().pause();
        saveResult();
        showWinLoseScreen(true);
    }

    private void saveResult() {
        try {
            GameBoard board = logicController.getBoard();
            int timeInSeconds = (int) (logicController.getTimer().getElapsedTime() / 1000);

            TextInputDialog dialog = new TextInputDialog("Player");
            dialog.setTitle("Поздравляем!");
            dialog.setHeaderText(String.format("Вы победили за %d секунд на поле %dx%d с %d бомбами!",
                    timeInSeconds, board.getWidth(), board.getHeight(), board.getTotalBombs()));
            dialog.setContentText("Введите ваше имя:");

            dialog.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
                Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setDisable(newVal.trim().isEmpty());
            });

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(playerName -> {
                try {
                    Score score = new Score(playerName, timeInSeconds, board.getWidth(), board.getHeight(), board.getTotalBombs());

                    GameSaveManager.saveToFile(score, GameSaveManager.SCORES_FILE);
                    System.out.println("Результат успешно сохранен!");

                } catch (IOException e) {
                    System.err.println("Ошибка при сохранении результата: " + e.getMessage());
                    showAlert("Не удалось сохранить результат", e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Произошла ошибка при сохранении результата: " + e.getMessage());
            showAlert("Не удалось сохранить результат", e.getMessage());
        }
    }

    private void resetGame() {

        if (timerAnimation != null) {
            timerAnimation.stop();
        }
        int width = logicController.getBoard().getWidth();
        int height = logicController.getBoard().getHeight();
        int bombs = logicController.getBoard().getTotalBombs();

        logicController = new GameLogicController(width, height, bombs);
        gameView = new GameView(logicController.getBoard());
        uiElements = new UIElements(logicController.getBoard(), logicController.getTimer());

        setupEventHandlers();
        startTimer();
        updateUI();
        showGameScene();
    }

    private void showWinLoseScreen(boolean isWin) {
        long elapsedTime = logicController.getTimer().getElapsedTime() / 1000;
        int bombsFound = logicController.getBoard().getTotalBombs() - logicController.getBoard().getRemainingFlags();
        int totalBombs = logicController.getBoard().getTotalBombs();

        WinLoseScreenView winLoseScreen = new WinLoseScreenView(primaryStage, isWin, (int) elapsedTime, bombsFound, totalBombs);

        winLoseScreen.getBackButton().setOnAction(event -> showMainMenu());
        winLoseScreen.getExitButton().setOnAction(event -> System.exit(0));
        winLoseScreen.getStatsButton().setOnAction(event -> showScores());

        primaryStage.setScene(winLoseScreen.getScene());
        primaryStage.show();
    }

    private void showScores() {
        Stage scoresStage = new Stage();
        ScoresWindowView scoresWindow = new ScoresWindowView(scoresStage);
        scoresStage.show();
    }

    private void showDifficultySelection() {
        DifficultySelectionView difficultySelection = new DifficultySelectionView(primaryStage);

        difficultySelection.getEasyButton().setOnAction(event -> startGame(9, 9, 10));
        difficultySelection.getMediumButton().setOnAction(event -> startGame(16, 16, 40));
        difficultySelection.getHardButton().setOnAction(event -> startGame(30, 16, 99));
        difficultySelection.getBackButton().setOnAction(event -> showMainMenu());
    }

    private void showMainMenu() {
        MainMenuView mainMenu = new MainMenuView(primaryStage);
        mainMenu.getNewGameButton().setOnAction(event -> showDifficultySelection());
        mainMenu.getLoadGameButton().setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("save.txt");
            dialog.setTitle("Загрузка игры");
            dialog.setHeaderText("Введите путь к файлу сохранения:");
            dialog.setContentText("Файл:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(filePath -> {
                try {
                    if (!FileValidator.isValidFile(filePath)) {
                        showAlert("Файл не найден", "Указанный файл не существует или недоступен.");
                        return;
                    }

                    GameBoard loadedBoard = GameFileLoader.loadGameFromFile(filePath);
                    int width = loadedBoard.getWidth();
                    int height = loadedBoard.getHeight();
                    int totalBombs = loadedBoard.getTotalBombs();

                    startGame(width, height, totalBombs);
                } catch (Exception e) {
                    showAlert("Не удалось загрузить игру", e.getMessage());
                }
            });
        });
        mainMenu.getExitButton().setOnAction(event -> primaryStage.close());

        primaryStage.setScene(mainMenu.getScene());
        primaryStage.setTitle("Сапёр");
        primaryStage.show();
    }

    private void startGame(int width, int height, int totalBombs) {
        new GameUIController(primaryStage, width, height, totalBombs);
    }
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
/* GameUIController
Описание : Управляет взаимодействием пользователя с графическим интерфейсом.

Методы :

setupEventHandlers(): Настраивает обработчики событий.
updateUI(): Обновляет интерфейс.*/