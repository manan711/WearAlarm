package com.example.finalprojectgroup4.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalprojectgroup4.adapter.AlarmAdapter;
import com.example.finalprojectgroup4.databinding.ActivityMainBinding;
import com.example.finalprojectgroup4.model.Alarm;
import com.example.finalprojectgroup4.utils.AlarmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding mainBinding;
//    private AlarmManager alarmManager;
//    private PendingIntent pendingIntent;
//    private TimePicker timePicker;
    private List<Alarm> myAlarms = new ArrayList<>();
    private AlarmAdapter myAdapter;
    Context context;
    private static final int ACTION_ADD = 711;
    private static final int REQUEST_CODE = 712;

    String alarmName = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        mainBinding.btnAdd.setOnClickListener(this);
//        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        init();

    }


    private void init() {
        setLayout();
        initAlarm();
    }

    private void setLayout() {
        mainBinding.wrcAlarm.setHasFixedSize(true);
        mainBinding.wrcAlarm.setEdgeItemsCenteringEnabled(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mainBinding.wrcAlarm.setLayoutManager(mLayoutManager);
    }


    private void initAlarm() {
        if (!Objects.equals(alarmName, "null")) {
            Alarm alarm = createAlarm(null, alarmName);
            updateAlarm(alarm, ACTION_ADD);
        }
    }

    private Alarm createAlarm(String id, String alarm) {
        if (id == null) {
            id = String.valueOf(System.currentTimeMillis());
        }
        return new Alarm(id, alarm);
    }

    private void updateAlarm(Alarm alarm, int action) {
        if (action == ACTION_ADD) {
            AlarmUtils.saveAlarm(alarm, this);

        }
        updateAlarmAdapter();
    }

    private void updateAlarmAdapter() {
        myAlarms.clear();
        myAlarms.addAll(AlarmUtils.getAllAlarms(this));
        bindAdapter();
    }

    private void bindAdapter() {
        myAdapter = new AlarmAdapter(myAlarms,getApplicationContext());
        mainBinding.wrcAlarm.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAlarmAdapter();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String alarmName = data.getStringExtra("alarmName"); // Use the same key to retrieve the value

            if (alarmName != null) {
                // Do something with the value
                this.alarmName = alarmName;
                mainBinding.tvAlarm.setText(alarmName);
            }
        }
        updateAlarmAdapter();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == mainBinding.btnAdd.getId()) {
           Intent intent = new Intent(this, AlarmActivity.class);
           startActivityForResult(intent, REQUEST_CODE);
        }
    }
}