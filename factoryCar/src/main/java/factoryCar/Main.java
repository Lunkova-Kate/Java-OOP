package factoryCar;

import factoryCar.controller.FactoryController;
import factoryCar.utilityForCar.Config;
import factoryCar.view.FactoryView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Загружаем конфигурацию
            Config config = new Config("start.properties");

            // 2. Создаем контроллер фабрики
            FactoryController factoryController = new FactoryController(config);

            // 3. Создаем и показываем GUI
            FactoryView factoryView = new FactoryView(factoryController, config, primaryStage);
            factoryView.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка при запуске приложения: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Запускаем JavaFX приложение
        launch(args);
    }
}