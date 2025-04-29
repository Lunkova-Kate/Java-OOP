package bombSquad.view;


import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import bombSquad.model.Cell;
import bombSquad.model.GameBoard;

public class GameView extends GridPane {
    public static final int CELL_SIZE = 32; //  клеткa в пикселях

    private final GameBoard board;
    private final ImageView[][] cellViews;


    private final Image closedCell = loadImage("/images/closed_cell.png");
    private final Image bomb = loadImage("/images/bomb.png");
    private final Image flag = loadImage("/images/flag.png");
    private final Image question = loadImage("/images/question.png");
    private final Image[] numbers = new Image[8];


    public GameView(GameBoard board) {
        this.board = board;
        this.cellViews = new ImageView[board.getWidth()][board.getHeight()];

        for (int i = 1; i <= 8; i++) {
            numbers[i - 1] = loadImage("/images/number_" + i + ".png");
        }

        initializeBoard();
    }

    public void initializeBoard() {
        this.getChildren().clear();

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                ImageView cellView = new ImageView(closedCell);
                this.setStyle("-fx-background-color: white");
                this.setGridLinesVisible(true);
                cellView.setFitWidth(CELL_SIZE);
                cellView.setFitHeight(CELL_SIZE);

                cellViews[x][y] = cellView;
                this.add(cellView, x, y);
            }
        }
    }


    public void updateCell(int x, int y) {
        if (!isValidCoordinates(x, y)) {
            throw new IllegalArgumentException("Недопустимые координаты: (" + x + ", " + y + ")");
        }

        Cell cell = board.getCells()[x][y];
        ImageView cellView = cellViews[x][y];

        switch (cell.getState()) {
            case OPENED -> cellView.setImage(cell.isBomb() ? bomb :
                    (cell.getBombsAround() > 0 ? numbers[cell.getBombsAround() - 1] : null));
            case FLAGGED -> cellView.setImage(flag);
            case QUESTIONED -> cellView.setImage(question);
            default -> cellView.setImage(closedCell);
        }
    }

    public void updateAllCells() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCells()[x][y];
                if (cell.isBomb()) {
                    cell.setState(Cell.CellState.OPENED); // Открываем клетки с бомбами
                }
                updateCell(x, y);
            }
        }
    }


    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Не удалось загрузить изображение: " + path);
            return null;
        }
    }

    public void setCellClickHandlers(EventHandler<MouseEvent> leftClickHandler, EventHandler<MouseEvent> rightClickHandler) {
        for (ImageView[] row : cellViews) {
            for (ImageView cell : row) {
                cell.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && leftClickHandler != null) {
                        leftClickHandler.handle(event);
                    } else if (event.getButton() == MouseButton.SECONDARY && rightClickHandler != null) {
                        rightClickHandler.handle(event);
                    }
                });
            }
        }
    }

    private boolean isValidCoordinates(int x, int y) {
        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
    }
}
