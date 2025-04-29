package bombSquad.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WinLoseScreenView {
    private Stage stage;
    private Label resultLabel;
    private Label timeLabel;
    private Button backButton;
    private Button statsButton;
    private Button exitButton;

    public WinLoseScreenView(Stage stage, boolean isWin, int time, int bombsFound, int totalBombs) {
        this.stage = stage;
        initializeUI(isWin, time, bombsFound, totalBombs);
    }

    private void initializeUI(boolean isWin, int time, int bombsFound, int totalBombs) {
        StackPane stackPane = new StackPane();
        ImageView background = new ImageView(new Image("images/begin.png"));
        background.setFitWidth(550);
        background.setFitHeight(550);
        background.setPreserveRatio(true);

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);

        resultLabel = new Label(isWin ? "Победа!" : "Поражение");
        resultLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " +
                (isWin ? "#2ecc71" : "#e74c3c") + ";");

        timeLabel = new Label("Время: " + time + " сек");
        timeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label bombsLabel = new Label("Мин найдено: " + bombsFound + "/" + totalBombs);
        bombsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);

        backButton = createStyledButton( "images/back.png");
        statsButton = createStyledButton( "images/statistic.png");
        exitButton = createStyledButton( "images/exit.png");

        buttons.getChildren().addAll(backButton, statsButton, exitButton);

        mainContainer.getChildren().addAll(resultLabel, timeLabel, bombsLabel, buttons);
        stackPane.getChildren().addAll(background, mainContainer);

        Scene scene = new Scene(stackPane, 550, 550);
        stage.setScene(scene);
        stage.setTitle(isWin ? "Победа!" : "Поражение");
    }

    private Button createStyledButton(String iconPath) {
        Button button = new Button();

        try {
            ImageView icon = new ImageView(new Image(iconPath));
            icon.setFitWidth(50);
            icon.setFitHeight(50);
            button.setGraphic(icon);
        } catch (Exception e) {
            System.out.println("Не удалось загрузить иконку: " + iconPath);
        }

        // Прозрачный стиль для кнопки
        button.setStyle("""
        -fx-background-color: transparent; /* Прозрачный фон */
        -fx-border-color: transparent;    /* Нет границ */
        -fx-padding: 30;                   /* Отступы вокруг иконки */
        -fx-cursor: hand;                 /* Курсор в виде руки */
    """);

        return button;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getStatsButton() {
        return statsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
    public Scene getScene() {
        return stage.getScene();
    }
}
