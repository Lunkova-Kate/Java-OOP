package bombSquad.view;


import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;

public class MainMenuView {
    private Stage stage;
    private Button newGameButton;
    private Button loadGameButton;
    private Button exitButton;

    public MainMenuView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    private void initializeUI() {
        int n1 = 400;
        int n2 = 400;
        StackPane stackPane = new StackPane();

        ImageView background = new ImageView(new Image("images/begin.png"));
        background.setFitWidth(n1);
        background.setFitHeight(n2);
        background.setPreserveRatio(true);

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);

        Label title = new Label("Сапёр");
        title.setStyle("""
        -fx-font-size: 50px;
        -fx-font-weight: bold;
        -fx-text-fill: white;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 5, 0.5, 0, 1);
    """);
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1.5), title);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.05);
        pulse.setToY(1.05);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();

        HBox buttonsContainer = new HBox(20);
        buttonsContainer.setAlignment(Pos.CENTER);

        ImageView newGameIcon = new ImageView(new Image("images/new_game.png"));
        newGameIcon.setFitWidth(50);
        newGameIcon.setFitHeight(50);
        newGameIcon.setPreserveRatio(true);
        newGameButton = new Button("", newGameIcon);

        ImageView loadGameIcon = new ImageView(new Image("images/load_game.png"));
        loadGameIcon.setFitWidth(50);
        loadGameIcon.setFitHeight(50);
        loadGameIcon.setPreserveRatio(true);
        loadGameButton = new Button("", loadGameIcon);

        ImageView exitIcon = new ImageView(new Image("images/exit.png"));
        exitIcon.setFitWidth(50);
        exitIcon.setFitHeight(50);
        exitIcon.setPreserveRatio(true);
        exitButton = new Button("", exitIcon);
        String transparentStyle = """
    -fx-background-color: transparent;
    -fx-border-color: transparent;
    -fx-padding: 5;
    -fx-cursor: hand;                 /* Курсор в виде руки */
""";


        newGameButton.setStyle(transparentStyle);
        loadGameButton.setStyle(transparentStyle);
        exitButton.setStyle(transparentStyle);


        buttonsContainer.getChildren().addAll(newGameButton, loadGameButton, exitButton);
        mainContainer.getChildren().addAll(title, buttonsContainer);

        stackPane.getChildren().addAll(background, mainContainer);



        Scene scene = new Scene(stackPane, n1, n2);
        stage.setScene(scene);
        stage.setTitle("Сапёр");
    }



    public Scene getScene() {
        return stage.getScene();
    }
    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getLoadGameButton() {
        return loadGameButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
/*Описание : Отображает главное меню игры.

Поля :

Button newGameButton, loadGameButton, exitButton.
Методы :

initializeUI(): Создает UI элементы главного меню.
Геттеры :

getNewGameButton(), getLoadGameButton(), getExitButton().
.*/