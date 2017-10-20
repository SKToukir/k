package com.vumobile.clubzed.model;

/**
 * Created by toukirul on 24/1/2017.
 */

public class GameActionClass {

    private String content_code;
    private String category_code;
    private String content_title;
    private String type;
    private String physicalFileName;
    private String zeid;
    private String path;
    private String imageUrl;
    private String bigImageUrl;
    private String likes;
    private String downloads;


    public String getLikes() {
        return likes;
    }


    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getContent_code() {
        return content_code;
    }

    public void setContent_code(String content_code) {
        this.content_code = content_code;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhysicalFileName() {
        return physicalFileName;
    }

    public void setPhysicalFileName(String physicalFileName) {
        this.physicalFileName = physicalFileName;
    }

    public String getZeid() {
        return zeid;
    }

    public void setZeid(String zeid) {
        this.zeid = zeid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }
}
