package factoryCar.view;

import factoryCar.controller.FactoryController;
import factoryCar.utilityForCar.Config;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;

public class FactoryView implements Observer {
    private final FactoryController factoryController;
    private final Config config;
    private Stage primaryStage;

    // Labels для отображения состояния складов
    private Label bodyStorageLabel;
    private Label engineStorageLabel;
    private Label accessoryStorageLabel;
    private Label carStorageLabel;
    private Label workersLabel;
    private Label dealersLabel;

    // Labels для отображения текущих задержек
    private Label bodySupplierDelayLabel;
    private Label engineSupplierDelayLabel;
    private Label accessorySupplierDelayLabel;
    private Label dealerDelayLabel;

    // Кнопки управления
    private Button startButton;
    private Button stopButton;

    // Ползунки для управления скоростью
    private Slider bodySupplierSlider;
    private Slider engineSupplierSlider;
    private Slider accessorySupplierSlider;
    private Slider dealerSlider;

    public FactoryView(FactoryController factoryController, Config config) {
        this.factoryController = factoryController;
        this.config = config;
    }

    public void show() {
        primaryStage = new Stage();
        primaryStage.setTitle("Car Factory Simulation");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        // Заголовки
        grid.add(new Label("Компонент"), 0, 0);
        grid.add(new Label("Количество"), 1, 0);
        grid.add(new Label("Задержка"), 2, 0);

        // Отображение информации о складах
        grid.add(new Label("Кузовы на складе:"), 0, 1);
        bodyStorageLabel = new Label("0");
        grid.add(bodyStorageLabel, 1, 1);

        grid.add(new Label("Двигатели на складе:"), 0, 2);
        engineStorageLabel = new Label("0");
        grid.add(engineStorageLabel, 1, 2);

        grid.add(new Label("Аксессуары на складе:"), 0, 3);
        accessoryStorageLabel = new Label("0");
        grid.add(accessoryStorageLabel, 1, 3);

        grid.add(new Label("Машины на складе:"), 0, 4);
        carStorageLabel = new Label("0");
        grid.add(carStorageLabel, 1, 4);

        // Информация из конфига
        grid.add(new Label("Рабочих:"), 0, 5);
        workersLabel = new Label(String.valueOf(config.getWorkers()));
        grid.add(workersLabel, 1, 5);

        grid.add(new Label("Дилеров:"), 0, 6);
        dealersLabel = new Label(String.valueOf(config.getDealers()));
        grid.add(dealersLabel, 1, 6);

        // Разделитель перед элементами управления
        Separator controlSeparator = new Separator();
        grid.add(controlSeparator, 0, 7, 3, 1);

        // Кнопки управления
        startButton = new Button("Старт фабрики");
        startButton.setOnAction(e -> startFactory());
        grid.add(startButton, 0, 8);

        stopButton = new Button("Стоп фабрики");
        stopButton.setOnAction(e -> stopFactory());
        stopButton.setDisable(true);
        grid.add(stopButton, 1, 8);

        // Разделитель перед ползунками
        Separator sliderSeparator = new Separator();
        grid.add(sliderSeparator, 0, 9, 3, 1);

        // Ползунки для управления скоростью поставщиков и дилеров
        grid.add(new Label("Скорость поставщиков кузовов:"), 0, 10);
        bodySupplierSlider = createSupplierSlider(config.getSupplierMinDelay(), config.getSupplierMaxDelay());
        grid.add(bodySupplierSlider, 1, 10);
        bodySupplierDelayLabel = new Label(config.getSupplierMinDelay() + " ms");
        grid.add(bodySupplierDelayLabel, 2, 10);

        grid.add(new Label("Скорость поставщиков двигателей:"), 0, 11);
        engineSupplierSlider = createSupplierSlider(config.getSupplierMinDelay(), config.getSupplierMaxDelay());
        grid.add(engineSupplierSlider, 1, 11);
        engineSupplierDelayLabel = new Label(config.getSupplierMinDelay() + " ms");
        grid.add(engineSupplierDelayLabel, 2, 11);

        grid.add(new Label("Скорость поставщиков аксессуаров:"), 0, 12);
        accessorySupplierSlider = createSupplierSlider(config.getSupplierMinDelay(), config.getSupplierMaxDelay());
        grid.add(accessorySupplierSlider, 1, 12);
        accessorySupplierDelayLabel = new Label(config.getSupplierMinDelay() + " ms");
        grid.add(accessorySupplierDelayLabel, 2, 12);

        grid.add(new Label("Скорость дилеров:"), 0, 13);
        dealerSlider = createDealerSlider(config.getDealerMinDelay(), config.getDealerMaxDelay());
        grid.add(dealerSlider, 1, 13);
        dealerDelayLabel = new Label(config.getDealerMinDelay() + " ms");
        grid.add(dealerDelayLabel, 2, 13);

        // Настройка слушателей для ползунков
        setupSliderListeners();

        // Создание сцены с увеличенным размером
        Scene scene = new Scene(grid, 500, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Настройка наблюдателей
        setupObservers();

        // Обработчик закрытия окна
        primaryStage.setOnCloseRequest(event -> {
            stopFactory(); // Остановить фабрику при закрытии
            Platform.exit(); // Завершить JavaFX
            System.exit(0); // Завершить программу
        });
    }

    private Slider createSupplierSlider(int min, int max) {
        Slider slider = new Slider(min, max, min);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((max - min) / 4);
        slider.setBlockIncrement(100);
        slider.setSnapToTicks(true);
        return slider;
    }

    private Slider createDealerSlider(int min, int max) {
        Slider slider = new Slider(min, max, min);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((max - min) / 4);
        slider.setBlockIncrement(100);
        slider.setSnapToTicks(true);
        return slider;
    }

    private void setupSliderListeners() {
        bodySupplierSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int delay = newVal.intValue();
            factoryController.setBodySupplierDelay(delay);
            bodySupplierDelayLabel.setText(delay + " ms");
        });

        engineSupplierSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int delay = newVal.intValue();
            factoryController.setEngineSupplierDelay(delay);
            engineSupplierDelayLabel.setText(delay + " ms");
        });

        accessorySupplierSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int delay = newVal.intValue();
            factoryController.setAccessorySupplierDelay(delay);
            accessorySupplierDelayLabel.setText(delay + " ms");
        });

        dealerSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int delay = newVal.intValue();
            factoryController.setDealerDelay(delay);
            dealerDelayLabel.setText(delay + " ms");
        });
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
            } else if (o == factoryController.getEngineStorage()) {
                engineStorageLabel.setText(String.valueOf(arg));
            } else if (o == factoryController.getAccessoryStorage()) {
                accessoryStorageLabel.setText(String.valueOf(arg));
            } else if (o == factoryController.getCarStorage()) {
                carStorageLabel.setText(String.valueOf(arg));
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
}