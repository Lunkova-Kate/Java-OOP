package bombSquad.view;



import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import bombSquad.model.GameBoard;
import bombSquad.model.GameTimer;

public class UIElements extends HBox {
    private final Label timerLabel;
    private final Label bombCounterLabel;
    private final Button smileyButton;

    private final Image smileyNormal = new Image(getClass().getResourceAsStream("/images/smiley_normal.png"));
    private final Image smileyWin = new Image(getClass().getResourceAsStream("/images/smiley_happy.png"));
    private final Image smileyLose = new Image(getClass().getResourceAsStream("/images/smiley_sad.png"));

    private GameBoard gameBoard; // Ссылка на игровое поле
    private GameTimer gameTimer; // Ссылка на таймер


    public UIElements(GameBoard gameBoard, GameTimer gameTimer) {
        this.gameBoard = gameBoard;
        this.gameTimer = gameTimer;

        setSpacing(20);
        setStyle("-fx-alignment: center; -fx-padding: 10;");

        timerLabel = new Label("000");
        timerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        smileyButton = new Button();
        smileyButton.setGraphic(new ImageView(smileyNormal));
        smileyButton.setStyle("-fx-background-color: transparent;-fx-cursor: hand;");

        bombCounterLabel = new Label(String.format("%3d", gameBoard.getRemainingFlags()));
        bombCounterLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        getChildren().addAll(timerLabel, smileyButton, bombCounterLabel);
    }

    public void updateTimer() {
        long elapsedTime = gameTimer.getElapsedTime() / 1000; // Время в секундах
        timerLabel.setText(String.format("%3d", elapsedTime));
    }


    public void updateBombCounter() {
        int remainingBombs = gameBoard.getRemainingFlags();
        bombCounterLabel.setText(String.format("%3d", remainingBombs));
    }


    public void setSmileyState(String state) {
        switch (state) {
            case "normal" -> smileyButton.setGraphic(new ImageView(smileyNormal));
            case "win" -> smileyButton.setGraphic(new ImageView(smileyWin));
            case "lose" -> smileyButton.setGraphic(new ImageView(smileyLose));
        }
    }

    public Button getSmileyButton() {
        return smileyButton;
    }
}

/* UIElements
Описание
Класс UIElements представляет собой контейнер для UI-элементов игры. Он содержит такие компоненты, как:

Таймер (отображает прошедшее время).
Счетчик бомб (отображает оставшееся количество бомб).
Кнопка "смайлик" (отображает текущее состояние игры: нормальное, победа или поражение).
Поля
Label timerLabel :
Отображает текущее время игры.
Label bombCounterLabel :
Отображает количество оставшихся бомб.
Button smileyButton :
Кнопка "смайлик", которая показывает состояние игры (нормальное, победа или поражение).
Image smileyNormal, Image smileyWin, Image smileyLose :
Изображения для различных состояний кнопки "смайлик".
Методы
Конструктор UIElements() :
Инициализирует все UI-элементы и задает их начальные стили.
Методы обновления состояния :
updateTimer(long seconds):
Обновляет значение таймера.
updateBombCounter(int bombs):
Обновляет значение счетчика бомб.
setSmileyState(String state):
Устанавливает состояние кнопки "смайлик" (нормальное, победа или поражение).
Геттеры :
getSmileyButton():
Возвращает кнопку "смайлик" для внешнего использования.*/
