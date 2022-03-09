package com.hohimlee.mpa.MainScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.hohimlee.mpa.R;

public class RecyclerView_details extends AppCompatActivity {

    String event, miles, route, duration, startTime, endTime, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_workrout_details);

        Intent intent1 = getIntent();
        event = intent1.getStringExtra("event");
        miles = intent1.getStringExtra("miles");
        route = intent1.getStringExtra("route");
        duration = intent1.getStringExtra("duration");
        startTime = intent1.getStringExtra("startTime");
        endTime = intent1.getStringExtra("endTime");
        date = intent1.getStringExtra("date");


    }
}