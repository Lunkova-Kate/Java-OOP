package factoryCar.view;

import factoryCar.controller.FactoryController;
import factoryCar.model.details.PartType;
import factoryCar.model.storage.AccessoryStorage;
import factoryCar.model.storage.BodyStorage;
import factoryCar.model.storage.CarStorage;
import factoryCar.model.storage.EngineStorage;
import factoryCar.threads.another.Dealer;
import factoryCar.threads.another.Worker;
import factoryCar.threads.supplier.AccessorySupplier;
import factoryCar.threads.supplier.BodySupplier;
import factoryCar.threads.supplier.EngineSupplier;
import factoryCar.utilityForCar.Config;
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

public class FactoryView implements Observer {
    private final FactoryController factoryController;
    private final Config config;
    private final Stage primaryStage;
    private final Pane animationPane = new Pane();
    private final Map<Class<?>, ImageView> componentViews = new HashMap<>();
    private final Map<PartType, ImageView> movingParts = new HashMap<>();

    // UI элементы
    private final Label bodyStorageLabel = new Label("0");
    private final Label engineStorageLabel = new Label("0");
    private final Label accessoryStorageLabel = new Label("0");
    private final Label carStorageLabel = new Label("0");
    private final Label workersLabel = new Label("0");
    private final Label dealersLabel = new Label("0");
    private final Label bodySupplierDelayLabel = new Label("0 ms");
    private final Label engineSupplierDelayLabel = new Label("0 ms");
    private final Label accessorySupplierDelayLabel = new Label("0 ms");
    private final Label dealerDelayLabel = new Label("0 ms");
    private final Button startButton = new Button("Start Production");
    private final Button stopButton = new Button("Stop Production");

    public FactoryView(FactoryController factoryController, Config config, Stage primaryStage) {
        this.factoryController = factoryController;
        this.config = config;
        this.primaryStage = primaryStage;
        initializeUI();
        setupMovingParts();
        setupObservers();
    }

    private void initializeUI() {
        primaryStage.setTitle("Car Factory Simulation");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));


        StackPane centerPane = new StackPane(createProductionScheme(), animationPane);
        root.setCenter(centerPane);

        root.setTop(createControlPanel());
        root.setRight(createControlsPanel()); // Панель с ползунками
        root.setLeft(createStatsPanel());

        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            stopFactory();
            Platform.exit();
            System.exit(0);
        });
    }

    private VBox createControlsPanel() {
        VBox controlsPanel = new VBox(15);
        controlsPanel.setPadding(new Insets(15));
        controlsPanel.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #d0d0d0; -fx-border-width: 0 0 0 1;");
        controlsPanel.setMinWidth(300);

        controlsPanel.getChildren().addAll(
                createControlSlider("Body Supplier Delay:", bodySupplierDelayLabel,
                        config.getSupplierMinDelay(), config.getSupplierMaxDelay(),
                        factoryController::setBodySupplierDelay),
                createControlSlider("Engine Supplier Delay:", engineSupplierDelayLabel,
                        config.getSupplierMinDelay(), config.getSupplierMaxDelay(),
                        factoryController::setEngineSupplierDelay),
                createControlSlider("Accessory Supplier Delay:", accessorySupplierDelayLabel,
                        config.getSupplierMinDelay(), config.getSupplierMaxDelay(),
                        factoryController::setAccessorySupplierDelay),
                createControlSlider("Dealer Delay:", dealerDelayLabel,
                        config.getDealerMinDelay(), config.getDealerMaxDelay(),
                        factoryController::setDealerDelay)
        );

        return controlsPanel;
    }

    private VBox createControlSlider(String title, Label valueLabel, int min, int max, java.util.function.Consumer<Integer> delaySetter) {
        VBox sliderBox = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Slider slider = new Slider(min, max, min);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((max - min) / 4);
        slider.setBlockIncrement(100);
        slider.setSnapToTicks(true);

        HBox valueBox = new HBox(5, new Label("Current:"), valueLabel);
        valueBox.setAlignment(Pos.CENTER_LEFT);

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int delay = newVal.intValue();
            delaySetter.accept(delay);
            valueLabel.setText(delay + " ms");
        });

        sliderBox.getChildren().addAll(titleLabel, slider, valueBox);
        return sliderBox;
    }

    private void setupMovingParts() {
        Image boxImage = new Image(getClass().getResourceAsStream("/images/box.png"));

        for (PartType type : PartType.values()) {
            ImageView partView = new ImageView(boxImage);
            partView.setFitWidth(20);
            partView.setPreserveRatio(true);
            partView.setVisible(false);
            movingParts.put(type, partView);
            animationPane.getChildren().add(partView);
        }
    }

    private Group createProductionScheme() {
        Group scheme = new Group();

        componentViews.put(BodySupplier.class, createComponentView("/images/supplies.png", 50, 100, "Body Supplier"));
        componentViews.put(EngineSupplier.class, createComponentView("/images/supplies.png", 50, 200, "Engine Supplier"));
        componentViews.put(AccessorySupplier.class, createComponentView("/images/supplies.png", 50, 300, "Accessory Supplier"));

        componentViews.put(BodyStorage.class, createComponentView("/images/storage.png", 200, 100, "Body Storage"));
        componentViews.put(EngineStorage.class, createComponentView("/images/storage.png", 200, 200, "Engine Storage"));
        componentViews.put(AccessoryStorage.class, createComponentView("/images/storage.png", 200, 300, "Accessory Storage"));

        componentViews.put(Worker.class, createComponentView("/images/worker.png", 400, 200, "Workers"));
        componentViews.put(CarStorage.class, createComponentView("/images/storage.png", 550, 200, "Car Storage"));
        componentViews.put(Dealer.class, createComponentView("/images/dealer.png", 700, 200, "Dealers"));

        createConnectionLines(scheme);

        scheme.getChildren().addAll(componentViews.values());
        return scheme;
    }

    private void createConnectionLines(Group scheme) {
        scheme.getChildren().addAll(
                createConnectionLine(120, 125, 175, 125), // Body
                createConnectionLine(120, 225, 175, 225), // Engine
                createConnectionLine(120, 325, 175, 325)  // Accessory
        );

        scheme.getChildren().addAll(
                createConnectionLine(270, 125, 400, 210), // Body to Worker
                createConnectionLine(270, 225, 400, 210), // Engine to Worker
                createConnectionLine(270, 325, 400, 210)  // Accessory to Worker
        );

        scheme.getChildren().addAll(
                createConnectionLine(450, 200, 525, 200), // Worker to CarStorage
                createConnectionLine(600, 200, 675, 200)  // CarStorage to Dealers
        );
    }

    private ImageView createComponentView(String imagePath, double x, double y, String tooltip) {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        view.setStyle("-fx-border-color: red; -fx-border-width: 2;");
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

    public void animatePartMovement(PartType type) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> animatePartMovement(type));
            return;
        }

        Path path = new Path();
        ImageView part = movingParts.get(type);

        switch(type) {
            case BODY:
                path.getElements().addAll(
                        new MoveTo(120, 125),
                        new LineTo(175, 125),
                        new LineTo(400, 210)
                );
                break;
            case ENGINE:
                path.getElements().addAll(
                        new MoveTo(120, 225),
                        new LineTo(175, 225),
                        new LineTo(400, 210)
                );
                break;
            case ACCESSORY:
                path.getElements().addAll(
                        new MoveTo(120, 325),
                        new LineTo(175, 325),
                        new LineTo(400, 210)
                );
                break;
            case CAR:
                path.getElements().addAll(
                        new MoveTo(450, 280),
                        new LineTo(525, 280),
                        new LineTo(675, 280)
                );
                break;
        }

        PathTransition transition = new PathTransition(Duration.seconds(1.5), path, part);
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

        startButton.setOnAction(e -> startFactory());
        stopButton.setOnAction(e -> stopFactory());

        return panel;
    }

    private VBox createStatsPanel() {
        workersLabel.setText(String.valueOf(config.getWorkers()));
        dealersLabel.setText(String.valueOf(config.getDealers()));
        bodySupplierDelayLabel.setText(config.getSupplierMinDelay() + " ms");
        engineSupplierDelayLabel.setText(config.getSupplierMinDelay() + " ms");
        accessorySupplierDelayLabel.setText(config.getSupplierMinDelay() + " ms");
        dealerDelayLabel.setText(config.getDealerMinDelay() + " ms");

        VBox panel = new VBox(15,
                new Label("Factory Statistics:"),
                new Separator(),
                createStatRow("Workers:", workersLabel),
                createStatRow("Dealers:", dealersLabel),
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

    private void setupObservers() {
        factoryController.getBodyStorage().addObserver(this);
        factoryController.getEngineStorage().addObserver(this);
        factoryController.getAccessoryStorage().addObserver(this);
        factoryController.getCarStorage().addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == null) return;

        Platform.runLater(() -> {
            if (o == factoryController.getBodyStorage()) {
                bodyStorageLabel.setText(String.valueOf(arg));
                animatePartMovement(PartType.BODY);
            } else if (o == factoryController.getEngineStorage()) {
                engineStorageLabel.setText(String.valueOf(arg));
                animatePartMovement(PartType.ENGINE);
            } else if (o == factoryController.getAccessoryStorage()) {
                accessoryStorageLabel.setText(String.valueOf(arg));
                animatePartMovement(PartType.ACCESSORY);
            } else if (o == factoryController.getCarStorage()) {
                carStorageLabel.setText(String.valueOf(arg));
                animatePartMovement(PartType.CAR);
            }
        });
    }

    private void startFactory() {
        try {
            factoryController.start();
            startButton.setDisable(true);
            stopButton.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopFactory() {
        factoryController.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    public void show() {
        primaryStage.show();
    }
}