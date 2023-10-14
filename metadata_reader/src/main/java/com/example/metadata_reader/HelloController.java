package com.example.metadata_reader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HelloController {

    private Stage stage;
    private Scene scene;

    private String path;

    private List<PDFFile> PDFFiles = new ArrayList<>();
    private int index = 0;



    public void switchToUploadedFilesWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("uploaded_files_window.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        index = 0;

    }

    public void switchToLoadFilesWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("load_file_window.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        index = 0;
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    private TextField path_text;
    final FileChooser fc = new FileChooser();


    @FXML
    void handleBtnOpenFile() {

        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select a Folder");

        File selectedDirectory = dc.showDialog(new Stage());


        if (selectedDirectory != null) {
            path_text.appendText(selectedDirectory.getAbsolutePath() + "\n");
            path = selectedDirectory.getAbsolutePath();
            PDFFiles = reader.getFiles(path);
        } else {
            System.out.println("No directory selected");
        }
        setInfo();
    }

    @FXML private TextField txtName;
    @FXML private TextField txtSize;
    @FXML private TextField txtPageSize;
    @FXML private TextField txtPageCount;
    @FXML private TextField txtTitle;
    @FXML private TextField txtMatter;
    @FXML private TextField txtKeyWords;
    @FXML private TextField txtTypePDFFile;
    @FXML private TextField txtPDFVersion;
    @FXML private TextField txtCreationApp;
    @FXML private TextField txtImages;
    @FXML private TextField txtFonts;
    @FXML private TextArea rtxtResumen;

    public void saveFile() {
        changePDFFile();

        reader.saveFiles(PDFFiles);

        txtName.setText("");
        txtSize.setText("");
        txtPageSize.setText("");
        txtPageCount.setText("");
        txtTitle.setText("");
        txtMatter.setText("");
        txtKeyWords.setText("");
        txtTypePDFFile.setText("");
        txtPDFVersion.setText("");
        txtCreationApp.setText("");
        txtImages.setText("");
        txtFonts.setText("");
        rtxtResumen.setText("");
    }

    public void changePDFFile() {
        PDFFiles.get(index).setName(txtName.getText());
        PDFFiles.get(index).setSize(Double.parseDouble(txtSize.getText()));
        PDFFiles.get(index).setPageSize(txtPageSize.getText());
        PDFFiles.get(index).setPageCount(Integer.valueOf(txtPageCount.getText()));
        PDFFiles.get(index).setTitle(txtTitle.getText());
        PDFFiles.get(index).setMatter(txtMatter.getText());
        PDFFiles.get(index).setKeyWords(txtKeyWords.getText());
        PDFFiles.get(index).setTypePDFFile(txtTypePDFFile.getText());
        PDFFiles.get(index).setVersion(txtPDFVersion.getText());
        PDFFiles.get(index).setCreationApp(txtCreationApp.getText());
        PDFFiles.get(index).setImages(Integer.valueOf(txtImages.getText()));
        PDFFiles.get(index).setFonts(Integer.valueOf(txtFonts.getText()));
        PDFFiles.get(index).setSummary(rtxtResumen.getText());
    }

    private void setInfo(){
        txtName.setText(PDFFiles.get(index).getName());
        txtSize.setText(String.valueOf(PDFFiles.get(index).getSize()));
        txtPageSize.setText(PDFFiles.get(index).getPageSize());
        txtPageCount.setText(String.valueOf(PDFFiles.get(index).getPageCount()));
        txtTitle.setText(PDFFiles.get(index).getTitle());
        txtMatter.setText(PDFFiles.get(index).getMatter());
        txtKeyWords.setText(PDFFiles.get(index).getKeyWords());
        txtTypePDFFile.setText(PDFFiles.get(index).getTypePDFFile());
        txtPDFVersion.setText(PDFFiles.get(index).getVersion());
        txtCreationApp.setText(PDFFiles.get(index).getCreationApp());
        txtImages.setText(String.valueOf(PDFFiles.get(index).getImages()));
        txtFonts.setText(String.valueOf(PDFFiles.get(index).getFonts()));
        rtxtResumen.setText(PDFFiles.get(index).getSummary());
    }

    public void forwardFileUpload() {
        changePDFFile();

        if (index == PDFFiles.size() - 1){
            index = 0;
        } else{
            index++;
        }
        setInfo();
    }

    public void backwardFileUpload() {
        changePDFFile();

        if (index == 0){
            index = PDFFiles.size() - 1;
        } else{
            index--;
        }
        setInfo();
    }

    public void forwardFileUploaded() {
        if (index == PDFFiles.size() - 1){
            index = 0;
        } else{
            index++;
        }
        setInfo();
    }

    public void backwardFileUploaded() {
        if (index == 0){
            index = PDFFiles.size() - 1;
        } else{
            index--;
        }
        setInfo();
    }

    public void clickLoadBtn() {
        PDFFiles = null;
        PDFFiles = reader.readFiles();
        setInfo();
    }
}