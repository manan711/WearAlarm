package com.example.finalprojectgroup4.activity;

import android.content.Context;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalprojectgroup4.databinding.ActivityEditBinding;
import com.example.finalprojectgroup4.model.Alarm;
import com.example.finalprojectgroup4.receiver.AlarmReceiver;
import com.example.finalprojectgroup4.utils.AlarmUtils;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityEditBinding editBinding;
    private String alarmId;
    private int setHour, setMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editBinding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(editBinding.getRoot());

        editBinding.selectTimeTV.setOnClickListener(this);
        editBinding.btnSet.setOnClickListener(this);
        editBinding.btnDelete.setOnClickListener(this);
        editBinding.btnCancel.setOnClickListener(this);

        alarmId = getIntent().getStringExtra("ALARM_ID");
        loadAlarmDetails(alarmId);
    }

    private void loadAlarmDetails(String alarmId) {
        Alarm alarm = AlarmUtils.getAlarmById(alarmId, this);
        if (alarm != null) {
            setHour = alarm.getHour();
            setMinute = alarm.getMinute();
            updateDisplayTime(setHour, setMinute);
        }
    }

    private void setTime() {
        Calendar currentTime = Calendar.getInstance();
        TimePickerDialog timePicker = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setHour = hourOfDay;
                setMinute = minute;
                updateDisplayTime(hourOfDay, minute);
            }
        }, setHour, setMinute, false); // Use false for 12-hour format
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    private void updateDisplayTime(int hourOfDay, int minute) {
        int displayHour = (hourOfDay > 12) ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
        String amPm = (hourOfDay < 12) ? "AM" : "PM";
        editBinding.selectTimeTV.setText(String.format("%02d:%02d %s", displayHour, minute, amPm));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == editBinding.selectTimeTV.getId()) {
            setTime();
        } else if (v.getId() == editBinding.btnSet.getId()) {
            String formattedTime = formatTime(setHour, setMinute);
            Alarm updatedAlarm = new Alarm(alarmId, formattedTime, setHour, setMinute);
            AlarmUtils.updateAlarm(updatedAlarm, this);
            scheduleAlarm(updatedAlarm);
            Toast.makeText(this, "Alarm updated to " + formattedTime, Toast.LENGTH_SHORT).show();
            finish();
        } else if (v.getId() == editBinding.btnDelete.getId()) {
            deleteAlarm();
        } else if (v.getId() == editBinding.btnCancel.getId()) {
            finish();
        }
    }

    private String formatTime(int hour, int minute) {
        String amPm;
        int displayHour = hour;

        if (hour == 0) {
            displayHour = 12;
            amPm = "AM";
        } else if (hour == 12) {
            amPm = "PM";
        } else if (hour > 12) {
            displayHour = hour - 12;
            amPm = "PM";
        } else {
            amPm = "AM";
        }

        return String.format("%02d:%02d %s", displayHour, minute, amPm);
    }


    private void updateAlarm() {
        Alarm updatedAlarm = new Alarm(alarmId, String.format("%02d:%02d", setHour, setMinute), setHour, setMinute);
        AlarmUtils.updateAlarm(updatedAlarm, this);
        scheduleAlarm(updatedAlarm);
        Toast.makeText(this, "Alarm updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void scheduleAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void deleteAlarm() {
        Alarm alarmToDelete = AlarmUtils.getAlarmById(alarmId, this);
        if (alarmToDelete != null) {
            cancelAlarm(alarmToDelete);
            AlarmUtils.deleteAlarm(alarmToDelete, this);
        }
        Toast.makeText(this, "Alarm deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void cancelAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
