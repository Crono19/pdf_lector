package com.example.metadata_reader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Objects;


public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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
        fc.setTitle("Leer PDF");

        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf*"));

        List<File> files = fc.showOpenMultipleDialog(null);

        for (int i = 0; i < files.size(); i++){

            if(files != null){
            path_text.appendText(files.get(i).getAbsolutePath() + "\n");
            } else {
                System.out.println("Invalid file");
            }
        }

    }


}