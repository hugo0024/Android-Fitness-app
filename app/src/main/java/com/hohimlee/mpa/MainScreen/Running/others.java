package com.hohimlee.mpa.MainScreen.Running;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.hohimlee.mpa.R;

public class others extends AppCompatActivity {

    TextInputLayout miles, route, event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        miles = findViewById(R.id.miles);
        route = findViewById(R.id.route);
        event = findViewById(R.id.event);

    }

    public void next(View view){

        if(!validateMiles() | !validateRoute() | !validateEvent() ){
            return;
        }

        Intent intent = new Intent(this, Running_Timer.class);
        String milesS = miles.getEditText().getText().toString().trim();
        String routeS = route.getEditText().getText().toString().trim();
        String eventS = event.getEditText().getText().toString().trim();

        intent.putExtra("miles", milesS);
        intent.putExtra("route", routeS);
        intent.putExtra("event", eventS);

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

    private boolean validateEvent() {
        String val = event.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            event.setError("Cannot be empty");
            return false;
        } else {
            event.setError(null);
            event.setErrorEnabled(false);
            return true;
        }
    }

    public void back(View view){
        super.onBackPressed();
    }
}