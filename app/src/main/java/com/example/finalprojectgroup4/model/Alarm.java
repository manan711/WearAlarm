package com.example.finalprojectgroup4.model;

public class Alarm {
    private String id = "";
    private String alarmText = "";
    private boolean isEnabled;

    public Alarm(String id, String alarmText) {
        this.id = id;
        this.alarmText = alarmText;
    }

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
    public boolean isEnabled() {
        return isEnabled;
    }
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
