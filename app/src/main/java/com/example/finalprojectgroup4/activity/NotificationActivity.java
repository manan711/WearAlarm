package com.example.finalprojectgroup4.activity;

import android.os.Bundle;
import android.view.View;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalprojectgroup4.databinding.ActivityNotificationBinding;
import com.example.finalprojectgroup4.receiver.AlarmReceiver;
import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityNotificationBinding notificationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationBinding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(notificationBinding.getRoot());
        notificationBinding.btnStop.setOnClickListener(this);
        notificationBinding.btnSnooze.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == notificationBinding.btnStop.getId()){
            stopAlarm();
        }
        if (v.getId() == notificationBinding.btnSnooze.getId()){
            snoozeAlarm();
        }
    }

    private void stopAlarm() {
        // Stop the alarm sound or notification if applicable
        // For example, if you're playing a sound in AlarmReceiver, stop it here
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void snoozeAlarm() {
        // Reschedule the alarm for a later time (e.g., 5 minutes later)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5); // Snooze for 5 minutes

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Alarm snoozed for 5 minutes", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
    }
}
