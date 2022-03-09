package com.hohimlee.mpa.MainScreen.Running;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hohimlee.mpa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Running_Timer extends AppCompatActivity {

    Dialog dialog;
    Dialog confirmDialog;

    TextView timerText, workoutTitle;
    Button stopStartButton;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    boolean timerStarted = false;
    boolean hasStarted = false;

    String event, miles, route, startTime, endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running__timer);

        timerText = (TextView) findViewById(R.id.timer_text);
        stopStartButton = (Button) findViewById(R.id.start_stop_timer);
        workoutTitle = findViewById(R.id.workout_title);
        timer = new Timer();

        Intent intent1 = getIntent();
        miles = intent1.getStringExtra("miles");
        route = intent1.getStringExtra("route");
        event = intent1.getStringExtra("event");

        workoutTitle .setText(event);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.yes_no_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_backgroud));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        Button yes = dialog.findViewById(R.id.btn_yes);
        Button no = dialog.findViewById(R.id.btn_cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmDialog = new Dialog(this);
        confirmDialog.setContentView(R.layout.complete_dialog);
        confirmDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_backgroud));
        confirmDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        confirmDialog.setCancelable(true);

        Button confirm = confirmDialog.findViewById(R.id.btn_confirm);
        Button notConfirm = confirmDialog.findViewById(R.id.btn_cancel_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(v);
            }
        });

        notConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });
    }

    private void next(View v) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        endTime = date.format(currentLocalTime);

        Intent intent = new Intent(this, Running_storeData.class);
        intent.putExtra("event", event);
        intent.putExtra("miles", miles);
        intent.putExtra("route", route);
        intent.putExtra("duration", getTimerText());
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
        confirmDialog.dismiss();
        startActivity(intent);
        this.finish();
    }


    public void completed(View view) {

        if(getTimerText().equals( "00 : 00 : 00")){
            Toast.makeText(this, "You need to start your run first", Toast.LENGTH_SHORT).show();
        }
        else{
            confirmDialog.show();
        }
    }

    public void onBackPressed() {
        dialog.show();
    }

    public void showDialog(View view) {
        dialog.show();
    }

    public void back(View view) {
        dialog.dismiss();
        super.onBackPressed();
    }

    public void startStopTapped(View view)
    {
        if(timerStarted == false)
        {
            if(hasStarted == false){
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0:00"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("HH:mm a");
                date.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
                startTime = date.format(currentLocalTime);
            }
            timerStarted = true;
            stopStartButton.setText("Pause");
            startTimer();
        }
        else
        {
            timerStarted = false;
            stopStartButton.setText("Start");
            timerTask.cancel();
        }
    }

    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }


}