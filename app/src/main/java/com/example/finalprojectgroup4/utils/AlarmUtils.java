package com.example.finalprojectgroup4.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.finalprojectgroup4.model.Alarm;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlarmUtils {

    private static final String ALARM_PREFERENCES = "AlarmPreferences";

    public static void saveAlarm(Alarm alarm, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALARM_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(alarm);

        editor.putString(alarm.getId(), json);
        editor.commit();
    }

    public static List<Alarm> loadAlarms(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALARM_PREFERENCES, MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        List<Alarm> alarmList = new ArrayList<>();
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = (String) entry.getValue();
            Alarm alarm = gson.fromJson(json, Alarm.class);
            alarmList.add(alarm);
        }

        return alarmList;
    }

    public static Alarm getAlarmById(String id, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALARM_PREFERENCES, MODE_PRIVATE);
        String json = sharedPreferences.getString(id, null);

        if (json != null) {
            Gson gson = new Gson();
            return gson.fromJson(json, Alarm.class);
        }
        return null;
    }

    public static void updateAlarm(Alarm alarm, Context context) {
        saveAlarm(alarm, context); // Since saveAlarm already saves the alarm, it can be used for updating too
    }

    public static void deleteAlarm(Alarm alarm, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ALARM_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(alarm.getId());
        editor.apply();
    }
}
