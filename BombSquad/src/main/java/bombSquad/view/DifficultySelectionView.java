package bombSquad.view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DifficultySelectionView {
    private Stage stage;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private Button customButton;
    private Button backButton;

    public DifficultySelectionView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    private void initializeUI() {
        StackPane stackPane = new StackPane();

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        ImageView background = new ImageView(new Image("images/begin.png"));
        background.setFitWidth(500);
        background.setFitHeight(500);
        background.setPreserveRatio(true);



        Label title = new Label("Выберите уровень сложности");
        title.setStyle("""
            -fx-font-size: 30px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 5, 0.5, 0, 1);
        """);

        easyButton = createStyledButton("Лёгкий", "9x9, 10 мин", "Идеально для новичков");
        mediumButton = createStyledButton("Средний", "16x16, 40 мин", "Стандартный уровень сложности");
        hardButton = createStyledButton("Сложный", "30x16, 99 мин", "Только для экспертов");

        backButton = createStyledButton("Назад", "", "Вернуться в меню");

        VBox buttons = new VBox(10, easyButton, mediumButton, hardButton,  backButton);
        buttons.setAlignment(Pos.CENTER);

        mainContainer.getChildren().addAll(title, buttons);
        stackPane.getChildren().add(mainContainer);

        Scene scene = new Scene(stackPane, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Выбор сложности");
    }

    private Button createStyledButton(String title, String subtitle, String tooltipText) {
        Button button = new Button();

        // Текст кнопки
        VBox textContainer = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e0e0e0;");

        textContainer.getChildren().addAll(titleLabel, subtitleLabel);
        textContainer.setAlignment(Pos.CENTER);

        button.setGraphic(textContainer);

        // Стиль кнопки
        button.setStyle("""
            -fx-background-color: rgba(0,0,0,0.3);
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-border-color: rgba(255,255,255,0.2);
            -fx-border-width: 1;
            -fx-padding: 10 25;
            -fx-cursor: hand;
            -fx-min-width: 200px;
        """);

        // Эффект при наведении
        button.setOnMouseEntered(e -> {
            button.setStyle("""
                -fx-background-color: rgba(0,0,0,0.5);
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: rgba(255,255,255,0.4);
                -fx-border-width: 1;
                -fx-padding: 10 25;
                -fx-cursor: hand;
                -fx-min-width: 200px;
            """);
        });

        button.setOnMouseExited(e -> {
            button.setStyle("""
                -fx-background-color: rgba(0,0,0,0.3);
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: rgba(255,255,255,0.2);
                -fx-border-width: 1;
                -fx-padding: 10 25;
                -fx-cursor: hand;
                -fx-min-width: 200px;
            """);
        });

        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(button, tooltip);

        return button;
    }

    public Button getEasyButton() {
        return easyButton;
    }

    public Button getMediumButton() {
        return mediumButton;
    }

    public Button getHardButton() {
        return hardButton;
    }

    public Button getCustomButton() {
        return customButton;
    }

    public Button getBackButton() {
        return backButton;
    }


}