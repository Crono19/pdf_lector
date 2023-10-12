package com.example.metadata_reader;

public class PDFFile {
    private String name; private double size; private String pageSize;
    private Integer pageCount; private String title; private String matter;
    private String keyWords; private String typePDFFile; private String version;
    private String creationApp; private Integer images; private Integer fonts;
    private String summary;

    public PDFFile(String name, double size, String pageSize, Integer pageCount, String title, String matter, String keyWords,
    String typePDFFile, String version, String creationApp, Integer images, Integer fonts, String summary) {
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
        this.summary = summary;
    }

    public String getChain(){
        return this.name + "/___/" + this.size + "/___/" + this.pageSize + "/___/" + this.pageCount + "/___/" + this.title + "/___/" + matter
                + "/___/" + this.keyWords + "/___/" + this.typePDFFile + "/___/" + this.version + "/___/" + this.creationApp + "/___/" + this.images + "/___/" + this.fonts;
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

    public String getSummary(){
        return summary;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSize(double size){
        this.size = size;
    }
    public void setPageSize(String pageSize){
        this.pageSize = pageSize;
    }

    public void setPageCount(Integer pageCount){
        this.pageCount = pageCount;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setMatter(String matter){
        this.matter = matter;
    }

    public void setKeyWords(String keyWords){
        this.keyWords = keyWords;
    }

    public void setTypePDFFile(String typePDFFile){
        this.typePDFFile = typePDFFile;
    }

    public void setVersion(String version){
        this.version = version;
    }

    public void setCreationApp(String creationApp){
        this.creationApp = creationApp;
    }

    public void setImages(Integer images){
        this.images = images;
    }

    public void setFonts(Integer fonts){
        this.fonts = fonts;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }
}
