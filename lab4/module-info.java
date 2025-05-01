module fabricCar {
    requires javafx.controls;
    requires javafx.fxml;

    opens fabricCar to javafx.fxml;
    exports fabricCar;
}