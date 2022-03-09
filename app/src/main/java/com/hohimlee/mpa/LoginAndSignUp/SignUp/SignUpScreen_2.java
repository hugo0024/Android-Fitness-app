package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;

import java.util.Calendar;

public class SignUpScreen_2 extends AppCompatActivity {

    ImageView backButton;
    Button next, login;
    TextView title;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen_2);

        backButton = findViewById(R.id.signUp_back_button);
        next = findViewById(R.id.signUp_next_button);
        login = findViewById(R.id.signUp_login_button);
        title = findViewById(R.id.signUp_title);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

    }

    public void nextSignUpScreen(View view) {

        if(!validateAge() | !validateGender()){
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String genderS = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String dateS = day+"/"+month+"/"+year;

        Intent intent = new Intent(getApplicationContext(), SignUpScreen3.class);

        Intent intent1 = getIntent();
        String firstNameS = intent1.getStringExtra("FirstName");
        String lastNameS = intent1.getStringExtra("lastName");
        String emailS = intent1.getStringExtra("email");
        String passwordS = intent1.getStringExtra("password");


        intent.putExtra("gender", genderS);
        intent.putExtra("date", dateS);
        intent.putExtra("FirstName", firstNameS);
        intent.putExtra("lastName", lastNameS);
        intent.putExtra("email", emailS);
        intent.putExtra("password", passwordS);


        startActivity(intent);

    }

    private boolean validateGender(){
        if(radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Please select your Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 3){
            Toast.makeText(this, "Not Valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public void back(View view){
        super.onBackPressed();
    }

    public void goToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        this.finish();
    }
}





