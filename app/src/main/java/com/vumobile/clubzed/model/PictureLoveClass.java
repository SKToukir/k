package com.vumobile.clubzed.model;

/**
 * Created by toukirul on 26/1/2017.
 */

public class PictureLoveClass {
    private String content_code;
    private String category_code;
    private String content_title;
    private String type;
    private String physicalFilwName;
    private String zeid;
    private String path;
    private String image_url;
    private String downloads;

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    private String likes;

    public String getLikes() {
        return likes;
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

    public String getPhysicalFilwName() {
        return physicalFilwName;
    }

    public void setPhysicalFilwName(String physicalFilwName) {
        this.physicalFilwName = physicalFilwName;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
