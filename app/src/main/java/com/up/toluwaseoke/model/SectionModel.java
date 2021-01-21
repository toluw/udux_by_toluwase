package com.up.toluwaseoke.model;

public class SectionModel {
    String title;
    String[]name,artwork;

    public SectionModel(String title, String[] name, String[] artwork) {
        this.title = title;
        this.name = name;
        this.artwork = artwork;
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
}
