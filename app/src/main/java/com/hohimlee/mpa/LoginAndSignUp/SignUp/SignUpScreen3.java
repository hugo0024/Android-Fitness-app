package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.LoginAndSignUp.phoneOTP;
import com.hohimlee.mpa.R;

public class SignUpScreen3 extends AppCompatActivity {

    TextView title;
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen3);

        title = findViewById(R.id.signUp_title);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        phoneNumber = findViewById(R.id.phoneNumber);


    }

    public void otpScreen(View view){

        if (!validatePhoneNumber()){
            return;
        }


        String firstNameS = getIntent().getStringExtra("FirstName");
        String lastNameS = getIntent().getStringExtra("lastName");
        String emailS = getIntent().getStringExtra("email");
        String passwordS = getIntent().getStringExtra("password");
        String genderS = getIntent().getStringExtra("gender");
        String dateS = getIntent().getStringExtra("date");
        String phoneNumberS = phoneNumber.getEditText().getText().toString().trim();
        if (phoneNumberS.charAt(0) == '0') {
            phoneNumberS = phoneNumberS.substring(1);
        }
        String fullPhoneNumberS ="+"+countryCodePicker.getFullNumber()+phoneNumberS;

        Intent intent  = new Intent(getApplicationContext(), phoneOTP.class);

        intent.putExtra("firstName", firstNameS);
        intent.putExtra("lastName", lastNameS);
        intent.putExtra("email", emailS);
        intent.putExtra("password", passwordS);
        intent.putExtra("gender", genderS);
        intent.putExtra("date", dateS);
        intent.putExtra("phoneNumber", phoneNumberS);
        intent.putExtra("fullPhoneNumber", fullPhoneNumberS);

        startActivity(intent);
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkSpaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;

        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
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