package com.hohimlee.mpa.MainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hohimlee.mpa.Helper.RunningHandler;
import com.hohimlee.mpa.R;

public class Running2 extends AppCompatActivity {

    String miles, duration, route, date;
    DatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running2);

        Intent intent1 = getIntent();
        miles = intent1.getStringExtra("miles");
        duration = intent1.getStringExtra("duration");
        route = intent1.getStringExtra("route");


        datePicker = findViewById(R.id.date_picker);
    }

    public void next(View view) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        date = day + "/" + month + "/" + year;

        storeData();
    }

    private void storeData() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = rootNode.getReference("Users");
        RunningHandler addNewData = new RunningHandler("Running", miles, duration, route, date);
        reference.child(user.getUid()).child("Workout").child(ts).setValue(addNewData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Running2.this, "Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Running2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        super.onBackPressed();
    }
}