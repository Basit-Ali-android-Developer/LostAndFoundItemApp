package com.example.runningapp.models;

public class notificationModel {
    private String message, notification_time;
    private int notification_id;

    public notificationModel(int notification_id, String message, String notification_time) {
        this.notification_id = notification_id;
        this.message = message;
        this.notification_time = notification_time;
    }

    public int getnotification_id() {
        return notification_id;
    }

    public String getmessage() {
        return message;
    }

    public String getnotification_time() {
        return notification_time;
    }
}
