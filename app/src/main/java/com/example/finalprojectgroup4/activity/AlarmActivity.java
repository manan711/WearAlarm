package com.example.finalprojectgroup4.activity;

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
import com.example.finalprojectgroup4.databinding.ActivityAlarmBinding;
import com.example.finalprojectgroup4.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.Objects;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAlarmBinding alarmBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm);
        alarmBinding= ActivityAlarmBinding.inflate(getLayoutInflater());
        View view = alarmBinding.getRoot();
        setContentView(view);
        alarmBinding.selectTimeTV.setOnClickListener(this);
        alarmBinding.btnSet.setOnClickListener(this);
        alarmBinding.btnCancel.setOnClickListener(this);
    };

    String alarmName = "null";
    int setHour, setMinute;
    public void setTime(){
        // Open TimePickerDialog
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                // Convert 24-hour time format to 12-hour format with AM/PM
                int hourOfDay = selectedHour;
                String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                hourOfDay = (hourOfDay > 12) ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);

                // Now you can use hourOfDay and selectedMinute for setting the alarm
                setHour = selectedHour;
                setMinute = selectedMinute;

                // Example: Display or use the selected time
                // String selectedTime = String.format("%02d:%02d %s", hourOfDay, selectedMinute, am_pm);
                // Log.d("SelectedTime", "Time: " + selectedTime);
                alarmBinding.selectTimeTV.setText(String.format("%02d:%02d %s", hourOfDay, selectedMinute, am_pm));
                alarmName = String.format("%02d:%02d %s", hourOfDay, selectedMinute, am_pm);
            }
        }, hour, minute, false); // false for 12-hour format
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    private void setAlarm(int hour, int minute) {
        // Create an intent that triggers the alarm BroadcastReceiver
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        // You can add extra data to the intent if needed
        // intent.putExtra("key", "value");

        // Create a PendingIntent that gets triggered when the alarm goes off
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Set the alarm to go off at the selected time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        // Set the alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == alarmBinding.selectTimeTV.getId()) {
            setTime();
        } else if (v.getId() == alarmBinding.btnSet.getId()) {
            if(!Objects.equals(alarmName, "null")) {
                setAlarm(setHour, setMinute);
                Intent intent = new Intent();
                intent.putExtra("alarmName", alarmName);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "Alarm set for " + alarmName, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == alarmBinding.btnCancel.getId()) {
            finish();
        }
    }
}
