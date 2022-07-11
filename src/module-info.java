module C482Project {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;
    exports main;
    opens controllers to javafx.fxml;
    exports controllers;
    opens model to javafx.fxml;
    exports model;
}