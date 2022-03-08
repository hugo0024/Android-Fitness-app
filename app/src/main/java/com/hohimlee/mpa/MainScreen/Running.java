package com.hohimlee.mpa.MainScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.hohimlee.mpa.R;

public class Running extends AppCompatActivity {

    TextInputLayout miles, duration, route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running);

        miles = findViewById(R.id.miles);
        duration= findViewById(R.id.duration);
        route = findViewById(R.id.route);

    }

    public void next(View view){

        if(!validateMiles() | !validateDuration() | !validateRoute()){
            return;
        }

        Intent intent = new Intent(this, Running2.class);
        String milesS = miles.getEditText().getText().toString().trim();
        String durationS = duration.getEditText().getText().toString().trim();
        String routeS = route.getEditText().getText().toString().trim();

        intent.putExtra("miles", milesS);
        intent.putExtra("duration", durationS);
        intent.putExtra("route", routeS);

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

    private boolean validateDuration() {
        String val = duration.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            duration.setError("Cannot be empty");
            return false;
        } else {
            duration.setError(null);
            duration.setErrorEnabled(false);
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