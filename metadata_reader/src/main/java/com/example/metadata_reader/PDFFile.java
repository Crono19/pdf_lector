package com.example.metadata_reader;

import java.util.List;

public class PDFFile {
    private final String name; private final double size; private final Integer pageNumber;
    private final String title; private final String matter; private final String keyWords;
    private final String typePDFFile; private final String version; private final String creationApp;

    public PDFFile(String name, double size, Integer pageNumber, String title, String matter, String keyWords,
    String typePDFFile, String version, String creationApp) {
        this.name = name;
        this.size = size;
        this.pageNumber = pageNumber;
        this.title = title;
        this.matter = matter;
        this.keyWords = keyWords;
        this.typePDFFile = typePDFFile;
        this.version = version;
        this.creationApp = creationApp;
    }

    public String getName(){
        return name;
    }

    public double getSize(){
        return size;
    }

    public Integer getPageNumber(){
        return pageNumber;
    }

    public String  getTitle(){
        return title;
    }

    public String getMatter(){
        return matter;
    }

    public String getKeyWords(){
        return keyWords;
    }

    public String  getTypePDFFile(){
        return typePDFFile;
    }

    public String  getVersion(){
        return version;
    }

    public String getCreationApp(){
        return creationApp;
    }
}
