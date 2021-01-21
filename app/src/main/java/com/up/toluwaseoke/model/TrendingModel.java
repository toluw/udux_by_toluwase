package com.up.toluwaseoke.model;

public class TrendingModel {
    String title;
    String[]name,artwork,source;

    public TrendingModel(String title, String[] name, String[] artwork, String[] source) {
        this.title = title;
        this.name = name;
        this.artwork = artwork;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getArtwork() {
        return artwork;
    }

    public void setArtwork(String[] artwork) {
        this.artwork = artwork;
    }

    public String[] getSource() {
        return source;
    }

    public void setSource(String[] source) {
        this.source = source;
    }
}
