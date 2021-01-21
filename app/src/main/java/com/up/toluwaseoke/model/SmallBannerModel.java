package com.up.toluwaseoke.model;

public class SmallBannerModel {
    private String[]title,artwork;

    public SmallBannerModel(String[] title, String[] artwork) {
        this.title = title;
        this.artwork = artwork;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getArtwork() {
        return artwork;
    }

    public void setArtwork(String[] artwork) {
        this.artwork = artwork;
    }
}
