package com.vumobile.clubzed.model;

/**
 * Created by toukirul on 29/1/2017.
 */

public class RelatedVideoClass {
    private String URL_RELATED_VIDEO;
    private String VIDEO_TITLE;
    private String VIDEO_LIKE;
    private String VIDEO_VIEWS;

    public String getVIDEO_LIKE() {
        return VIDEO_LIKE;
    }

    public void setVIDEO_LIKE(String VIDEO_LIKE) {
        this.VIDEO_LIKE = VIDEO_LIKE;
    }

    public String getVIDEO_VIEWS() {
        return VIDEO_VIEWS;
    }

    public void setVIDEO_VIEWS(String VIDEO_VIEWS) {
        this.VIDEO_VIEWS = VIDEO_VIEWS;
    }

    public String getVIDEO_TITLE() {
        return VIDEO_TITLE;
    }

    public void setVIDEO_TITLE(String VIDEO_TITLE) {
        this.VIDEO_TITLE = VIDEO_TITLE;
    }

    public String getURL_RELATED_VIDEO() {
        return URL_RELATED_VIDEO;
    }

    public void setURL_RELATED_VIDEO(String URL_RELATED_VIDEO) {
        this.URL_RELATED_VIDEO = URL_RELATED_VIDEO;
    }
}
