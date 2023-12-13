package com.example.finalprojectgroup4.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalprojectgroup4.databinding.ActivityAlarmBinding;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID; // Import UUID

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAlarmBinding alarmBinding;
    private int setHour, setMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmBinding = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(alarmBinding.getRoot());
        alarmBinding.selectTimeTV.setOnClickListener(this);
        alarmBinding.btnSet.setOnClickListener(this);
        alarmBinding.btnCancel.setOnClickListener(this);
    }

    public void setTime() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                setHour = selectedHour;
                setMinute = selectedMinute;

                // Convert to 12-hour format for display
                int displayHour = (selectedHour > 12) ? selectedHour - 12 : (selectedHour == 0 ? 12 : selectedHour);
                String amPm = (selectedHour < 12) ? "AM" : "PM";
                alarmBinding.selectTimeTV.setText(String.format("%02d:%02d %s", displayHour, selectedMinute, amPm));
            }
        }, hour, minute, false); // false for 12-hour format
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == alarmBinding.selectTimeTV.getId()) {
            setTime();
        } else if (v.getId() == alarmBinding.btnSet.getId()) {
            if(!Objects.equals(alarmBinding.selectTimeTV.getText(), "Select a time")) {
                Intent intent = new Intent();
                String uniqueID = UUID.randomUUID().toString(); // Generate a unique ID
                intent.putExtra("alarmId", uniqueID); // Pass the new unique ID
                intent.putExtra("setHour", setHour);
                intent.putExtra("setMinute", setMinute);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "Alarm set for " + String.format("%02d:%02d", setHour, setMinute), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == alarmBinding.btnCancel.getId()) {
            finish();
        }
    }
}
