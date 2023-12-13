package com.example.finalprojectgroup4.model;

public class Alarm {
    private String id;
    private String alarmText;
    private int hour;
    private int minute;

    // Constructor
    public Alarm(String id, String alarmText, int hour, int minute) {
        this.id = id;
        this.alarmText = alarmText;
        this.hour = hour;
        this.minute = minute;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlarmText() {
        return alarmText;
    }

    public void setAlarmText(String alarmText) {
        this.alarmText = alarmText;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    // Method to get a formatted time string
    public String getFormattedTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    // Method to get a unique request code
    public int getRequestCode() {
        return (id != null) ? id.hashCode() : 0;
    }
}
