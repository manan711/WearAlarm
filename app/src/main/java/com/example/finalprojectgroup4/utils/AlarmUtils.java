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

public class AlarmUtils {

    public static void saveAlarm(Alarm alarm, Context context) {
        if (alarm != null) {
            SharedPreferences sharedPref = context.getSharedPreferences("alarm_details", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(alarm.getId(), alarm.getAlarmText());
            editor.commit();
        }
    }

    public static List<Alarm> getAllAlarms(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("alarm_details", Context.MODE_PRIVATE);
        List<Alarm> alarmList = new ArrayList<>();
        Map<String, ?> map = sharedPref.getAll();

        Set set = map.entrySet();
        Iterator itr = set.iterator();

        while(itr.hasNext())
        {
            Map.Entry entry = (Map.Entry)itr.next();
            String savedAlarm = (String) entry.getValue();
            if(savedAlarm != null) {
                Alarm alarm = new Alarm(entry.getKey().toString(), savedAlarm);
                alarmList.add(alarm);
            }
        }
        return alarmList;
    }


}
