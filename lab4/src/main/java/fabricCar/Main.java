package fabricCar;

import fabricCar.controller.FactoryController;
import fabricCar.utilitesforcar.Config;
import fabricCar.view.FactoryView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Config config = new Config("/factory.cfg");
            FactoryView factoryView = new FactoryView(primaryStage);
            FactoryController controller = new FactoryController(config, factoryView);
            factoryView.show();
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
        }
    }
}