package com.example.finalprojectgroup4.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.finalprojectgroup4.model.Alarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

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


}
