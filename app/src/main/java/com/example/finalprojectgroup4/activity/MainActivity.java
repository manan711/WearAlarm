package com.example.finalprojectgroup4.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.finalprojectgroup4.adapter.AlarmAdapter;
import com.example.finalprojectgroup4.databinding.ActivityMainBinding;
import com.example.finalprojectgroup4.model.Alarm;
import com.example.finalprojectgroup4.receiver.AlarmReceiver;
import com.example.finalprojectgroup4.utils.AlarmUtils;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding mainBinding;
    private List<Alarm> myAlarms;
    private AlarmAdapter myAdapter;
    private static final int REQUEST_CODE = 712;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        mainBinding.btnAdd.setOnClickListener(this);
        init();
    }

    private void init() {
        setLayout();
        loadAlarms();
    }

    private void setLayout() {
        mainBinding.wrcAlarm.setHasFixedSize(true);
        mainBinding.wrcAlarm.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadAlarms() {
        myAlarms = AlarmUtils.loadAlarms(this);
        bindAdapter();
    }

    private void bindAdapter() {
        myAdapter = new AlarmAdapter(myAlarms, this);
        mainBinding.wrcAlarm.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAlarms();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String alarmId = data.getStringExtra("alarmId");
            int setHour = data.getIntExtra("setHour", 0);
            int setMinute = data.getIntExtra("setMinute", 0);

            String formattedTime = formatTime(setHour, setMinute);
            Alarm newAlarm = new Alarm(alarmId, formattedTime, setHour, setMinute);
            scheduleAlarm(newAlarm);
            AlarmUtils.saveAlarm(newAlarm, this);
            loadAlarms();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == mainBinding.btnAdd.getId()) {
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
