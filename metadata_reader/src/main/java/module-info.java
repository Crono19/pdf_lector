module com.example.metadata_reader {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.metadata_reader to javafx.fxml;
    exports com.example.metadata_reader;
}