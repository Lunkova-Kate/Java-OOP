package bombSquad.view;

import bombSquad.model.GameSaveManager;
import bombSquad.model.Score;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ScoresWindowView {
    private Stage stage;

    public ScoresWindowView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    private void initializeUI() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label titleLabel = new Label("Таблица рекордов");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<Score> tableView = createScoresTable();

        try {
            loadScoresIntoTable(tableView);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке таблицы рекордов: " + e.getMessage());
        }

        root.getChildren().addAll(titleLabel, tableView);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Таблица рекордов");
    }

    private TableView<Score> createScoresTable() {
        TableView<Score> tableView = new TableView<>();

        TableColumn<Score, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        nameColumn.setPrefWidth(150);

        TableColumn<Score, String> timeColumn = new TableColumn<>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedTime"));
        timeColumn.setPrefWidth(100);

        TableColumn<Score, String> sizeColumn = new TableColumn<>("Размер");
        sizeColumn.setCellValueFactory(cellData -> {
            Score score = cellData.getValue();
            return new SimpleStringProperty(score.getWidth() + "x" + score.getHeight());
        });
        sizeColumn.setPrefWidth(100);

        TableColumn<Score, Integer> bombsColumn = new TableColumn<>("Бомбы");
        bombsColumn.setCellValueFactory(new PropertyValueFactory<>("bombs"));
        bombsColumn.setPrefWidth(80);

        TableColumn<Score, String> difficultyColumn = new TableColumn<>("Сложность");
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        difficultyColumn.setPrefWidth(120);

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(timeColumn);
        tableView.getColumns().add(sizeColumn);
        tableView.getColumns().add(bombsColumn);
        tableView.getColumns().add(difficultyColumn);
        return tableView;
    }

    private void loadScoresIntoTable(TableView<Score> tableView) throws IOException {
        GameSaveManager saveManager = new GameSaveManager();
        List<Score> scores = saveManager.loadScores();

        if (scores.isEmpty()) {
            System.out.println("Таблица рекордов пуста.");
            return;
        }

        ObservableList<Score> observableScores = FXCollections.observableArrayList(scores);
        tableView.setItems(observableScores);
    }
}