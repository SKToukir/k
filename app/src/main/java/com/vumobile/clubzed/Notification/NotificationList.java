package com.vumobile.clubzed.Notification;

/**
 * Created by IT-10 on 10/10/2016.
 */
public class NotificationList {
    private String image_title;
    private String image_url;
    private String Push_Message;

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPush_Message() {
        return Push_Message;
    }

    public void setPush_Message(String push_Message) {
        Push_Message = push_Message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
