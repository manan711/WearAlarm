package com.example.finalprojectgroup4.activity;

import android.os.Bundle;
import android.view.View;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalprojectgroup4.R;
import com.example.finalprojectgroup4.databinding.ActivityMainBinding;
import com.example.finalprojectgroup4.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityNotificationBinding notificationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_notification);
        notificationBinding= ActivityNotificationBinding.inflate(getLayoutInflater());
        View view = notificationBinding.getRoot();
        setContentView(view);
        notificationBinding.btnStop.setOnClickListener(this);
        notificationBinding.btnSnooze.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {

    }
}
