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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;


public class HelloController {

    private Stage stage;
    private Scene scene;
    private String path;

    private List<PDFFile> files = new ArrayList<>();

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
        List<String> paths = new ArrayList<>();

        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select a Folder");

        File selectedDirectory = dc.showDialog(new Stage());

        if (selectedDirectory != null) {
            path_text.appendText(selectedDirectory.getAbsolutePath() + "\n");
            path = selectedDirectory.getAbsolutePath();

            reader.resortFolder(path, paths);

            for (String pdfFilePath : paths) {
                File pdfFile = new File(pdfFilePath);

                try (PDDocument document = Loader.loadPDF(pdfFile)) {
                    PDDocumentInformation info = document.getDocumentInformation();

                    String name = pdfFile.getName();
                    String title = info.getTitle();
                    String creator = info.getCreator();
                    String pdfVersion = String.valueOf(document.getVersion());
                    long fileSize = pdfFile.length();
                    int pageCount = document.getNumberOfPages();
                    String keywords = info.getKeywords();
                    PDFTextStripper stripper = new PDFTextStripper();
                    String matter = stripper.getText(document);

                    files.add(new PDFFile(name, fileSize, pageCount, title, matter, keywords, "PDF", pdfVersion, creator));

                } catch (IOException e) {
                    e.printStackTrace(System.out);
                    System.out.println("Error reading PDF file: " + pdfFilePath);
                }
            }
        } else {
            System.out.println("No directory selected");
        }

        for (PDFFile file : files){
            System.out.println(file.getName());
        }
    }
}