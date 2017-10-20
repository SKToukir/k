package com.vumobile.clubzed.Notification;

import java.util.Vector;

/**
 * Created by IT-10 on 10/10/2016.
 */
public class AllNotificationList {

    public static Vector<NotificationList> notificationListVector = new Vector<NotificationList>();


    public static Vector<NotificationList> getAllNotificationList() {
        return notificationListVector;
    }

    public static void setAllNotificationList(Vector<NotificationList> notificationListVector) {
        AllNotificationList.notificationListVector = notificationListVector;
    }

    public static NotificationList getNotificationList(int pos) {
        return notificationListVector.elementAt(pos);
    }

    public static void setNotificationList(NotificationList notificationList) {
        AllNotificationList.notificationListVector.addElement(notificationList);
    }

    public static void removeNotificationList() {
        AllNotificationList.notificationListVector.removeAllElements();
    }

}