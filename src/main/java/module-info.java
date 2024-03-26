module com.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.librarymanagement to javafx.fxml;
    exports com.example.librarymanagement;
}