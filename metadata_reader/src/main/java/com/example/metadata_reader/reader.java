package com.example.metadata_reader;

import javafx.scene.control.Alert;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.RenderedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class reader {
    public static void resortFolder(String path, List<String> filePaths) {

        File rootFolder = new File(path);

        File[] files = rootFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (isPDF(file.getAbsolutePath())){
                        filePaths.add(file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    resortFolder(file.getPath(), filePaths);
                }
            }
        }
    }

    public static boolean isPDF(String path) {
        return path.toLowerCase().endsWith(".pdf");
    }

    public static List<PDFFile> getFiles(String path){
        List<String> paths = new ArrayList<>();
        List<PDFFile> PDFFiles = new ArrayList<>();

        reader.resortFolder(path, paths);

        for (String pdfFilePath : paths) {
            File pdfFile = new File(pdfFilePath);

            try (PDDocument document = Loader.loadPDF(pdfFile)) {
                PDDocumentInformation info = document.getDocumentInformation();

                String name = pdfFile.getName();
                String title = info.getTitle();
                String creator = info.getCreator();
                String matter = info.getSubject();
                String pdfVersion = String.valueOf(document.getVersion());
                long fileSize = pdfFile.length();
                int pageCount = document.getNumberOfPages();
                String keywords = info.getKeywords();
                Integer images = getImagesFromPDF(document).size();
                Integer fonts = getImageFonts(document);
                String pageSize = getPageSize(document);
                String summarize;

                if (getSummarize(document).isEmpty()){
                    summarize = String.valueOf(createSummarize(document));
                }
                else{
                    summarize = getSummarize(document);
                }

                PDFFiles.add(new PDFFile(name, fileSize, pageSize, pageCount, title, matter, keywords, "PDF", pdfVersion,
                        creator, images, fonts, summarize));

            } catch (IOException e) {
                e.printStackTrace(System.out);
                System.out.println("Error reading PDF file: " + pdfFilePath);
            }
        }
        return PDFFiles;
    }

    public static List<RenderedImage> getImagesFromPDF(PDDocument file) throws IOException {
        List<RenderedImage> images = new ArrayList<>();
        for (PDPage page : file.getPages()) {
            images.addAll(getImagesFromResources(page.getResources()));
        }
        return images;
    }

    private static List<RenderedImage> getImagesFromResources(PDResources resources) throws IOException {
        List<RenderedImage> images = new ArrayList<>();

        for (COSName xObjectName : resources.getXObjectNames()) {
            PDXObject xObject = resources.getXObject(xObjectName);

            if (xObject instanceof PDFormXObject) {
                images.addAll(getImagesFromResources(((PDFormXObject) xObject).getResources()));
            } else if (xObject instanceof PDImageXObject) {
                images.add(((PDImageXObject) xObject).getImage());
            }
        }
        return images;
    }

    public static String getSummarize(PDDocument document) throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(document);
        String[] lines = pdfText.split("\n");

        StringBuilder summary = new StringBuilder();
        boolean isSummarySection = false;

        for (String line : lines) {
            if (((line.contains("Resumen")) || (line.contains("resumen")) || (line.contains("RESUMEN"))
                    || (line.contains("Presentación")) || (line.contains("presentación")) || (line.contains("PRESENTACIÓN")))
                    && ((!line.contains("....")) && (line.length() > 10))) {
                isSummarySection = true;
            } else if (isSummarySection && line.trim().isEmpty()) {
                break;
            } else if (isSummarySection) {
                summary.append(line).append("\n");
            }
        }

        return summary.toString();
    }

    private static List<String> createSummarize(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        List<String> summary = new ArrayList<>();
        String[] sentences = text.split("\\.");

        for (String sentence : sentences) {
            sentence = sentence.trim();

            if (sentence.split(" ").length > 10) {
                summary.add(sentence);
            }
        }

        return summary;
    }

    public static int getImageFonts(PDDocument document) throws IOException {
        int count = 0;

        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(document);

        String[] words = pdfText.split("\\s+");

        for (String w : words) {
            if ((w.equalsIgnoreCase("Fuente:")) || (w.equalsIgnoreCase("fuente:"))
            || (w.equalsIgnoreCase("FUENTE:"))) {
                count++;
            }
        }

        return count;
    }

    public static String getPageSize(PDDocument file) {
        DecimalFormat df = new DecimalFormat("###.#");
        PDPage page = file.getPage(0);

        String pageSize;

        PDRectangle mediaBox = page.getMediaBox();

        double width = mediaBox.getWidth() / 72;
        double height = mediaBox.getHeight() / 72;

        if (((Objects.equals(df.format(width), "8.5")) || ((Objects.equals(df.format(height), "11.0"))))) {
            pageSize = "Carta";
        } else if (((Objects.equals(df.format(width), "8.3")) || ((Objects.equals(df.format(height), "11.8"))))) {
            pageSize = "Oficio";
        } else {
            pageSize = df.format(width) + " x " + df.format(height);
        }
        return pageSize;
    }

    private static String getProjectRootDirectory() {
        return System.getProperty("user.dir");
    }

    public static void saveFiles(List<PDFFile> files) {
        File file = new File(getProjectRootDirectory() + File.separator + "filesmetadata.txt");

        if (file.exists()){
            file.delete();
        }

        for (PDFFile pdfFile : files) {
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                pw.println(pdfFile.getChain());
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }

        file = new File(getProjectRootDirectory() + File.separator + "filessummary.txt");

        if (file.exists()){
            file.delete();
        }

        for (PDFFile pdfFile : files) {
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                pw.println(pdfFile.getSummary() + "ESTE ES EL SEPARADOR DE RESUMENES:");
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public static List<PDFFile> readFiles(){
        File file = new File(getProjectRootDirectory() + File.separator + "filesmetadata.txt");

        if (file.exists()) {
            List<PDFFile> PDFFiles = new ArrayList<>();
            List<String> summarizes = readSummary();
            int index = 0;

            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null){
                    String[] parts = line.split("/___/");

                    String name = parts[0];
                    String size = parts[1];
                    String pageSize = parts[2];
                    String pageCount = parts[3];
                    String title = parts[4];
                    String matter = parts[5];
                    String keyWords = parts[6];
                    String typePDFFile = parts[7];
                    String version = parts[8];
                    String creationApp = parts[9];
                    String images = parts[10];
                    String fonts = parts[11];
                    assert summarizes != null;
                    String summary = summarizes.get(index);

                    PDFFiles.add(new PDFFile(name, Double.parseDouble(size) , pageSize, Integer.parseInt(pageCount),
                            title, matter, keyWords, typePDFFile, version, creationApp, Integer.parseInt(images),
                            Integer.parseInt(fonts), summary));

                    line = br.readLine();
                    index++;
                }
                br.close();
            } catch (IOException ex){
                ex.printStackTrace(System.out);
            }
            return PDFFiles;
        } else {
            showAlert();
            return null;
        }
    }

    public static List<String> readSummary(){
        String fileName = getProjectRootDirectory() + File.separator + "filessummary.txt";

        if (new File(fileName).exists()){
            List<String> summarizes = new ArrayList<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                String line;
                StringBuilder currentResumen = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    if (line.contains("ESTE ES EL SEPARADOR DE RESUMENES:")) {
                        if (!currentResumen.isEmpty()) {
                            summarizes.add(currentResumen.toString());
                            currentResumen = new StringBuilder();
                        }
                    } else {
                        currentResumen.append(line).append("\n");
                    }
                }

                if (!currentResumen.isEmpty()) {
                    summarizes.add(currentResumen.toString());
                }

                br.close();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
            return summarizes;
        } else{
            showAlert();
            return null;
        }
    }

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Mensaje importante");
        alert.setContentText("No se han guardado archivos para leer");
        alert.showAndWait().ifPresent(rs -> {});
    }
}