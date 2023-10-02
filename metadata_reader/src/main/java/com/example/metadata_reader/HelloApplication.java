package com.example.metadata_reader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        stage.setTitle("Metadata PDF Reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        List<String> paths = new ArrayList<>();

        reader.resortFolder("C:\\Users\\Pablo\\OneDrive\\Pablo\\Universidad\\Ciclo 4\\Manejo e implementación de archivos", paths);

        for (String path : paths){
            System.out.println(path);
        }

        for (String pdfFilePath : paths) {
            File pdfFile = new File(pdfFilePath);

            if (!pdfFile.exists() || !pdfFile.isFile() || !pdfFilePath.toLowerCase().endsWith(".pdf")) {
                System.out.println("Invalid PDF file: " + pdfFilePath);
                continue;
            }

            try (PDDocument document = Loader.loadPDF(pdfFile)) {
                PDDocumentInformation info = document.getDocumentInformation();

                String title = info.getTitle();
                String author = info.getAuthor();
                String creator = info.getCreator();
                String pdfVersion = String.valueOf(document.getVersion());
                long fileSize = pdfFile.length();

                System.out.println("File: " + pdfFilePath);
                System.out.println("Size: " + fileSize + " bytes");
                System.out.println("Title: " + pdfFile.getName());
                System.out.println("Author: " + author);
                System.out.println("Creator: " + creator);
                System.out.println("PDF Version: " + pdfVersion);

            } catch (IOException e) {
                e.printStackTrace(System.out);
                System.out.println("Error reading PDF file: " + pdfFilePath);
            }
        }
    }
}