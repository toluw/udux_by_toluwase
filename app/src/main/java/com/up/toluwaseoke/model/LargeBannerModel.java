package com.up.toluwaseoke.model;

public class LargeBannerModel {
    private String[]title,subtitle,mobile_artwork;

    public LargeBannerModel(String[] title, String[] subtitle, String[] mobile_artwork) {
        this.title = title;
        this.subtitle = subtitle;
        this.mobile_artwork = mobile_artwork;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String[] subtitle) {
        this.subtitle = subtitle;
    }

    public String[] getMobile_artwork() {
        return mobile_artwork;
    }

    public void setMobile_artwork(String[] mobile_artwork) {
        this.mobile_artwork = mobile_artwork;
    }
}
