package bombSquad;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import bombSquad.controller.GameUIController;
import bombSquad.files.FileValidator;
import bombSquad.files.GameFileLoader;
import bombSquad.model.GameBoard;
import bombSquad.view.MainMenuView;

import bombSquad.view.DifficultySelectionView;

import java.util.Optional;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainMenu();
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
                        showAlert("Ошибка", "Файл не найден", "Указанный файл не существует или недоступен.");
                        return;
                    }

                    GameBoard loadedBoard = GameFileLoader.loadGameFromFile(filePath);
                    int width = loadedBoard.getWidth();
                    int height = loadedBoard.getHeight();
                    int totalBombs = loadedBoard.getTotalBombs();

                    startGame(width, height, totalBombs);
                } catch (Exception e) {
                    showAlert("Ошибка", "Не удалось загрузить игру", e.getMessage());
                }
            });
        });

        mainMenu.getExitButton().setOnAction(event -> System.exit(0));

        primaryStage.setScene(mainMenu.getScene());
        primaryStage.setTitle("Сапёр");
        primaryStage.show();
    }

    private void showDifficultySelection() {
        DifficultySelectionView difficultySelection = new DifficultySelectionView(primaryStage);
        difficultySelection.getEasyButton().setOnAction(event -> startGame(9, 9, 10));
        difficultySelection.getMediumButton().setOnAction(event -> startGame(16, 16, 40));
        difficultySelection.getHardButton().setOnAction(event -> startGame(30, 16, 99));

        difficultySelection.getBackButton().setOnAction(event -> showMainMenu());
    }

    private void startGame(int width, int height, int totalBombs) {
        GameUIController gameUIController = new GameUIController(primaryStage, width, height, totalBombs);
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}