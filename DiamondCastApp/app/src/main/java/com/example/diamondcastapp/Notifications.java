package com.example.diamondcastapp;

import java.util.ArrayList;

public class Notifications {
    private ArrayList<Notification> notificationList;

    //different constructors that may be needed
    public Notifications() {
        this.notificationList = new ArrayList<>();
    }

    public Notifications(Notification notification) {
        this.notificationList.add(notification);
    }

    public Notifications(ArrayList<Notification> notifications) {
        this.notificationList = notifications;
    }

    public ArrayList<Notification> getNotificationList() {
        return this.notificationList;
    }

    public void setNotificationList(ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public void addNotification(Notification notification) {
        this.notificationList.add(notification);
    }

    public void removeNotification(Notification notification) {
        this.notificationList.remove(notification);
    }
}
