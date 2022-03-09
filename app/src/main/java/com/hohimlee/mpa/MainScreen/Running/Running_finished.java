package com.hohimlee.mpa.MainScreen.Running;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.hohimlee.mpa.MainScreen.MainScreen;
import com.hohimlee.mpa.R;

public class Running_finished extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running3);
    }

    public void back(View view){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void finish(View view){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
        this.finish();
    }

}