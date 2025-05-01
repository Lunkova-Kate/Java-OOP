package fabricCar.view;

import fabricCar.model.*;
import fabricCar.model.Storage.*;
import fabricCar.model.details.PartType;
import fabricCar.model.supplier.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class FactoryView implements StorageObserver {
    private final Stage primaryStage;
    private final Pane animationPane = new Pane();
    private final Map<Class<?>, ImageView> componentViews = new HashMap<>();
    private final Map<PartType, ImageView> movingParts = new HashMap<>();
    private final Map<ImageView, VBox> statsWindows = new HashMap<>();
    private final Map<Class<?>, Slider> speedSliders = new HashMap<>();

    // UI элементы
    private final Label bodyStorageLabel = new Label("0");
    private final Label engineStorageLabel = new Label("0");
    private final Label accessoryStorageLabel = new Label("0");
    private final Label carStorageLabel = new Label("0");
    private final Label producedCarsLabel = new Label("0");
    private final Label soldCarsLabel = new Label("0");
    private final Label tasksQueueLabel = new Label("0");
    private final Button startButton = new Button("Start Production");
    private final Button stopButton = new Button("Stop Production");

    // Статистика
    private int totalBodiesProduced = 0;
    private int totalEnginesProduced = 0;
    private int totalAccessoriesProduced = 0;

    public FactoryView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeUI();
        setupMovingParts();
        setupComponentClickHandlers();
    }

    private void initializeUI() {
        primaryStage.setTitle("Car Factory Simulation");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Основная схема производства
        StackPane centerPane = new StackPane(createProductionScheme(), animationPane);
        root.setCenter(centerPane);

        root.setTop(createControlPanel());
        root.setRight(createStatsPanel());

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
    }

    private void setupMovingParts() {
        Image boxImage = new Image(getClass().getResourceAsStream("/images/box.png"));

        for (PartType type : PartType.values()) {
            ImageView partView = new ImageView(boxImage);
            partView.setFitWidth(30);
            partView.setPreserveRatio(true);
            partView.setVisible(false);
            movingParts.put(type, partView);
            animationPane.getChildren().add(partView);
        }
    }

    private void setupComponentClickHandlers() {
        componentViews.forEach((cls, imageView) -> {
            imageView.setOnMouseClicked(e -> showStatsWindow(cls, imageView));
        });
    }

    private Group createProductionScheme() {
        Group scheme = new Group();

        // Создаем визуальные компоненты для каждого типа
        componentViews.put(BodySupplier.class, createComponentView("/images/supplies.png", 50, 100, "Body Supplier"));
        componentViews.put(EngineSupplier.class, createComponentView("/images/supplies.png", 50, 200, "Engine Supplier"));
        componentViews.put(AccessorySupplier.class, createComponentView("/images/supplies.png", 50, 300, "Accessory Supplier"));

        componentViews.put(BodyStorage.class, createComponentView("/images/storage.png", 200, 100, "Body Storage"));
        componentViews.put(EngineStorage.class, createComponentView("/images/storage.png", 200, 200, "Engine Storage"));
        componentViews.put(AccessoryStorage.class, createComponentView("/images/storage.png", 200, 300, "Accessory Storage"));

        componentViews.put(Worker.class, createComponentView("/images/worker.png", 400, 200, "Workers"));
        componentViews.put(CarStorage.class, createComponentView("/images/storage.png", 550, 200, "Car Storage"));
        componentViews.put(Dealer.class, createComponentView("/images/dealer.png", 700, 200, "Dealers"));

        // Соединительные линии
        createConnectionLines(scheme);

        // Добавляем все компоненты в схему
        scheme.getChildren().addAll(componentViews.values());
        return scheme;
    }

    private void createConnectionLines(Group scheme) {
        // Горизонтальные линии от поставщиков к складам
        scheme.getChildren().addAll(
                createConnectionLine(120, 125, 175, 125), // Body
                createConnectionLine(120, 225, 175, 225), // Engine
                createConnectionLine(120, 325, 175, 325)  // Accessory
        );

        // Линии от складов к рабочим
        scheme.getChildren().addAll(
                createConnectionLine(270, 125, 400, 210), // Body to Worker
                createConnectionLine(270, 225, 400, 210), // Engine to Worker
                createConnectionLine(270, 325, 400, 210)  // Accessory to Worker
        );

        // Линии от рабочих к складу машин и дилерам
        scheme.getChildren().addAll(
                createConnectionLine(450, 200, 525, 200), // Worker to CarStorage
                createConnectionLine(600, 200, 675, 200)  // CarStorage to Dealers
        );
    }

    private ImageView createComponentView(String imagePath, double x, double y, String tooltip) {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        view.setFitWidth(50);
        view.setPreserveRatio(true);
        view.setX(x);
        view.setY(y);
        view.setCursor(javafx.scene.Cursor.HAND);
        Tooltip.install(view, new Tooltip(tooltip));
        return view;
    }

    private Line createConnectionLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(2);
        line.getStrokeDashArray().addAll(5d, 5d);
        return line;
    }

    private void showStatsWindow(Class<?> componentClass, ImageView source) {
        if (statsWindows.containsKey(source)) {
            statsWindows.get(source).setVisible(true);
            return;
        }

        VBox statsBox = new VBox(10);
        statsBox.setPadding(new Insets(15));
        statsBox.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-width: 1;");

        if (componentClass == BodyStorage.class || componentClass == EngineStorage.class ||
                componentClass == AccessoryStorage.class || componentClass == CarStorage.class) {

            Label currentLabel = new Label("Current: 0");
            Label totalLabel = new Label("Total: 0");
            Slider speedSlider = new Slider(0.1, 2.0, 1.0);
            speedSlider.setShowTickLabels(true);
            speedSlider.setShowTickMarks(true);
            speedSlider.setMajorTickUnit(0.5);
            speedSlider.setMinorTickCount(1);
            speedSlider.setSnapToTicks(true);

            statsBox.getChildren().addAll(
                    new Label(componentClass.getSimpleName() + " Stats"),
                    new Separator(),
                    currentLabel,
                    totalLabel,
                    new Label("Speed multiplier:"),
                    speedSlider
            );

            // Сохраняем ссылки для обновления
            if (componentClass == BodyStorage.class) {
                currentLabel.textProperty().bind(bodyStorageLabel.textProperty());
                totalLabel.setText("Total: " + totalBodiesProduced);
            } else if (componentClass == EngineStorage.class) {
                currentLabel.textProperty().bind(engineStorageLabel.textProperty());
                totalLabel.setText("Total: " + totalEnginesProduced);
            } else if (componentClass == AccessoryStorage.class) {
                currentLabel.textProperty().bind(accessoryStorageLabel.textProperty());
                totalLabel.setText("Total: " + totalAccessoriesProduced);
            } else if (componentClass == CarStorage.class) {
                currentLabel.textProperty().bind(carStorageLabel.textProperty());
                totalLabel.textProperty().bind(producedCarsLabel.textProperty().map(t -> "Total: " + t));
            }

            speedSliders.put(componentClass, speedSlider);
        } else if (componentClass == Dealer.class) {
            statsBox.getChildren().addAll(
                    new Label("Dealers Stats"),
                    new Separator(),
                    new Label("Cars sold: "),
                    soldCarsLabel
            );
        }

        // Кнопка закрытия
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> statsBox.setVisible(false));
        statsBox.getChildren().add(closeButton);

        statsBox.setLayoutX(source.getLayoutX() + 60);
        statsBox.setLayoutY(source.getLayoutY());
        animationPane.getChildren().add(statsBox);
        statsWindows.put(source, statsBox);
    }

    public void animatePartMovement(PartType type) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> animatePartMovement(type));
            return;
        }

        double speedFactor = 1.0;
        if (type == PartType.BODY && speedSliders.containsKey(BodyStorage.class)) {
            speedFactor = speedSliders.get(BodyStorage.class).getValue();
        } else if (type == PartType.ENGINE && speedSliders.containsKey(EngineStorage.class)) {
            speedFactor = speedSliders.get(EngineStorage.class).getValue();
        } else if (type == PartType.ACCESSORY && speedSliders.containsKey(AccessoryStorage.class)) {
            speedFactor = speedSliders.get(AccessoryStorage.class).getValue();
        } else if (type == PartType.CAR && speedSliders.containsKey(CarStorage.class)) {
            speedFactor = speedSliders.get(CarStorage.class).getValue();
        }

        Path path = new Path();
        ImageView part = movingParts.get(type);

        // Определяем путь в зависимости от типа детали
        switch(type) {
            case BODY:
                path.getElements().addAll(new MoveTo(100, 125), new LineTo(175, 125), new LineTo(375, 200));
                break;
            case ENGINE:
                path.getElements().addAll(new MoveTo(100, 225), new LineTo(175, 225), new LineTo(375, 200));
                break;
            case ACCESSORY:
                path.getElements().addAll(new MoveTo(100, 325), new LineTo(175, 325), new LineTo(375, 200));
                break;
            case CAR:
                path.getElements().addAll(new MoveTo(450, 200), new LineTo(525, 200), new LineTo(675, 200));
                break;
        }

        PathTransition transition = new PathTransition(Duration.seconds(1.5 / speedFactor), path, part);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setInterpolator(Interpolator.LINEAR);

        part.setVisible(true);
        transition.setOnFinished(e -> part.setVisible(false));
        transition.play();
    }

    private HBox createControlPanel() {
        HBox panel = new HBox(10, startButton, stopButton);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #e0e0e0;");

        startButton.setStyle("-fx-font-weight: bold;");
        stopButton.setStyle("-fx-font-weight: bold;");
        stopButton.setDisable(true);

        return panel;
    }

    private VBox createStatsPanel() {
        VBox panel = new VBox(15,
                new Label("Factory Statistics:"),
                new Separator(),
                createStatRow("Produced Cars:", producedCarsLabel),
                createStatRow("Sold Cars:", soldCarsLabel),
                createStatRow("Tasks in Queue:", tasksQueueLabel),
                new Separator(),
                new Label("Storage Status:"),
                createStatRow("Bodies:", bodyStorageLabel),
                createStatRow("Engines:", engineStorageLabel),
                createStatRow("Accessories:", accessoryStorageLabel),
                createStatRow("Cars:", carStorageLabel)
        );

        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: #f0f0f0;");
        panel.setMinWidth(250);

        return panel;
    }

    private HBox createStatRow(String label, Label valueLabel) {
        valueLabel.setStyle("-fx-font-weight: bold;");
        return new HBox(10, new Label(label), valueLabel);
    }

    // Реализация StorageObserver
    @Override
    public void onBodyCountChanged(int count) {
        Platform.runLater(() -> bodyStorageLabel.setText(String.valueOf(count)));
    }

    @Override
    public void onEngineCountChanged(int count) {
        Platform.runLater(() -> engineStorageLabel.setText(String.valueOf(count)));
    }

    @Override
    public void onAccessoryCountChanged(int count) {
        Platform.runLater(() -> accessoryStorageLabel.setText(String.valueOf(count)));
    }

    @Override
    public void onCarCountChanged(int count) {
        Platform.runLater(() -> carStorageLabel.setText(String.valueOf(count)));
    }

    @Override
    public void onCarProduced(int totalProduced) {
        Platform.runLater(() -> {
            producedCarsLabel.setText(String.valueOf(totalProduced));
            animatePartMovement(PartType.CAR);
        });
    }

    @Override
    public void onCarSold(int totalSold) {
        Platform.runLater(() -> soldCarsLabel.setText(String.valueOf(totalSold)));
    }

    @Override
    public void onTaskQueueChanged(int tasksInQueue) {
        Platform.runLater(() -> tasksQueueLabel.setText(String.valueOf(tasksInQueue)));
    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(() -> showAlert("Factory Message", message));
    }

    public void show() {
        primaryStage.show();
    }

    public void setStartAction(Runnable action) {
        startButton.setOnAction(e -> action.run());
    }

    public void setStopAction(Runnable action) {
        stopButton.setOnAction(e -> action.run());
    }

    public void setProductionStarted(boolean started) {
        Platform.runLater(() -> {
            startButton.setDisable(started);
            stopButton.setDisable(!started);
        });
    }

    public Map<Class<?>, Slider> getSpeedSliders() {
        return speedSliders;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.show();
    }
}