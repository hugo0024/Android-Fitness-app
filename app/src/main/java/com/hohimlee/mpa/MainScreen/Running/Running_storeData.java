package com.hohimlee.mpa.MainScreen.Running;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hohimlee.mpa.Helper.RecyclerView_data_handler;
import com.hohimlee.mpa.MainScreen.MainScreen;
import com.hohimlee.mpa.MainScreen.MainScreenFragment;
import com.hohimlee.mpa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Running_storeData extends AppCompatActivity {

    TextView finishTime, finishMiles, workoutTitle, workoutTo;
    String event, miles, route, duration, startTime, endTime, date;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running2);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        Intent intent1 = getIntent();
        event = intent1.getStringExtra("event");
        miles = intent1.getStringExtra("miles");
        route = intent1.getStringExtra("route");
        duration = intent1.getStringExtra("duration");
        startTime = intent1.getStringExtra("startTime");
        endTime = intent1.getStringExtra("endTime");
        date = df.format(c);

        workoutTitle = findViewById(R.id.workout_title);
        workoutTo = findViewById(R.id.workout_to);
        finishTime = findViewById(R.id.finish_time);
        finishMiles = findViewById(R.id.finish_miles);
        finishTime.setText(duration);
        finishMiles.setText(miles + " miles");
        workoutTitle.setText(event);

        if(event.equals("Running")){
            workoutTo.setText("to run");
        }
        else if(event.equals("Cycling")){
            workoutTo.setText("to cycle");
        }
        else if(event.equals("Swimming")){
            workoutTo.setText("to swim");
        }

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.yes_no_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_backgroud));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        Button yes = dialog.findViewById(R.id.btn_yes);
        Button no = dialog.findViewById(R.id.btn_cancel);
        TextView desc = dialog.findViewById(R.id.textView2);

        desc.setText("Your result has not been saved");

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
    }

    public void next(View view) {
        storeData();
    }

    private void storeData() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = rootNode.getReference("Users");
        RecyclerView_data_handler addNewData = new RecyclerView_data_handler(event, miles, route, duration, startTime, endTime, date);
        reference.child(user.getUid()).child("Workout").child(ts).setValue(addNewData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    goNext();
                } else {
                    Toast.makeText(Running_storeData.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goNext() {
        Intent intent = new Intent(this, Running_finished.class);
        startActivity(intent);
        this.finish();
    }

    public void showDialog(View view) { dialog.show(); }

    public void back(View view) {
        dialog.dismiss();
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed() { dialog.show(); }
}