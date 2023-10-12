package com.example.metadata_reader;

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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
                String pdfVersion = String.valueOf(document.getVersion());
                long fileSize = pdfFile.length();
                int pageCount = document.getNumberOfPages();
                String keywords = info.getKeywords();
                PDFTextStripper stripper = new PDFTextStripper();
                String matter = stripper.getText(document);
                Integer images = getImagesFromPDF(document).size();
                Integer fonts = extractFontNamesFromPDF(document).size();
                String pageSize = getPageSize(document);


                PDFFiles.add(new PDFFile(name, fileSize, pageSize, pageCount, title, matter, keywords, "PDF", pdfVersion,
                        creator, images, fonts));

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

    public static Set<String> extractFontNamesFromPDF(PDDocument file) throws IOException {
        Set<String> fontNames = new HashSet<>();

        for (PDPage page : file.getPages()) {
            for (COSName font : page.getResources().getFontNames()) {
                fontNames.add(font.getName());
            }
        }
        file.close();
        return fontNames;
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
        File file = new File(getProjectRootDirectory() + File.separator + "files.txt");

        for (PDFFile pdfFile : files) {
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                pw.println(pdfFile.getChain());
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

}
