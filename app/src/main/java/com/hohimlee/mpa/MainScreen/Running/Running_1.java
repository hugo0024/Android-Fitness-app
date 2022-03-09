package com.hohimlee.mpa.MainScreen.Running;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hohimlee.mpa.R;

public class Running_1 extends AppCompatActivity {

    TextView workoutTitle;
    TextInputLayout miles, route;
    String event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running);

        workoutTitle = findViewById(R.id.workout_title);
        miles = findViewById(R.id.miles);
        route = findViewById(R.id.route);

        Intent intent1 = getIntent();
        event = intent1.getStringExtra("event");

        workoutTitle.setText(event);
    }

    public void next(View view){

        if(!validateMiles() | !validateRoute()){
            return;
        }

        Intent intent = new Intent(this, Running_Timer.class);
        String milesS = miles.getEditText().getText().toString().trim();
        String routeS = route.getEditText().getText().toString().trim();

        intent.putExtra("miles", milesS);
        intent.putExtra("route", routeS);
        intent.putExtra("event", event);

        startActivity(intent);

    }

    private boolean validateMiles() {
        String val = miles.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            miles.setError("Cannot be empty");
            return false;
        } else {
            miles.setError(null);
            miles.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateRoute() {
        String val = route.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            route.setError("Cannot be empty");
            return false;
        } else {
            route.setError(null);
            route.setErrorEnabled(false);
            return true;
        }
    }

    public void back(View view){
        super.onBackPressed();
    }
}