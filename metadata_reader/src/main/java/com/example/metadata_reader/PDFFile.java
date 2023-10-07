package com.example.metadata_reader;

import java.util.List;

public class PDFFile {
    private final String name; private final double size; private final String pageSize;
    private final Integer pageCount; private final String title; private final String matter;
    private final String keyWords; private final String typePDFFile; private final String version;
    private final String creationApp; private final Integer images; private final Integer fonts;

    public PDFFile(String name, double size, String pageSize, Integer pageCount, String title, String matter, String keyWords,
    String typePDFFile, String version, String creationApp, Integer images, Integer fonts) {
        this.name = name;
        this.size = size;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.title = title;
        this.matter = matter;
        this.keyWords = keyWords;
        this.typePDFFile = typePDFFile;
        this.version = version;
        this.creationApp = creationApp;
        this.images = images;
        this.fonts = fonts;
    }

    public String getChain(){
        return this.name + "|" + this.size + "|" + this.pageSize + "|" + this.pageCount + "|" + this.title + "|" + this.matter
                + "|" + this.keyWords + "|" + this.typePDFFile + "|" + this.version + "|" + this.creationApp + "|" + this.images + "|" + this.fonts;
    }

    public String getName(){
        return name;
    }

    public double getSize(){
        return size;
    }
    public String getPageSize(){
        return pageSize;
    }

    public Integer getPageCount(){
        return pageCount;
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

    public Integer getImages(){
        return images;
    }
    public Integer getFonts(){
        return fonts;
    }


}
