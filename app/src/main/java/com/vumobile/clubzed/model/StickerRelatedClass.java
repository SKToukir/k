package com.vumobile.clubzed.model;

/**
 * Created by toukirul on 13/2/2017.
 */

public class StickerRelatedClass {

    private String picTitle;
    private String picImageUrl;
    private String likes;
    private String downloads;

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public String getPicImageUrl() {
        return picImageUrl;
    }

    public void setPicImageUrl(String picImageUrl) {
        this.picImageUrl = picImageUrl;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
