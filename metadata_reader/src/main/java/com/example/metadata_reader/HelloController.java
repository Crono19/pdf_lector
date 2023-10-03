package com.example.metadata_reader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import java.io.IOException;
import java.io.File;
import java.util.Objects;


public class HelloController {

    private Stage stage;
    private Scene scene;
    private String path;

    public void switchToUploadedFilesWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("uploaded_files_window.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToLoadFilesWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("load_file_window.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    private Button load_btn;

    @FXML
    private TextArea path_text;
    final FileChooser fc = new FileChooser();

    @FXML
    void handleBtnOpenFile(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select a Folder");

        File selectedDirectory = dc.showDialog(new Stage());

        if (selectedDirectory != null) {
            path_text.appendText(selectedDirectory.getAbsolutePath() + "\n");
            path = selectedDirectory.getAbsolutePath();
        } else {
            System.out.println("No directory selected");
        }
    }
}