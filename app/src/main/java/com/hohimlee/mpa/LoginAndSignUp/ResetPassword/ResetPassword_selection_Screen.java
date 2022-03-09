package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hohimlee.mpa.R;

public class ResetPassword_selection_Screen extends AppCompatActivity {

    TextView emailText, phoneText;
    Button email_button, sms_button;
    String fullPhoneNumberS, emailS, passwordS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password_selection__screen);

        emailText = findViewById(R.id.user_email);
        phoneText = findViewById(R.id.user_phone);
        email_button = findViewById(R.id.email_button);
        sms_button = findViewById(R.id.SMS_button);

        emailS = getIntent().getStringExtra("email");
        passwordS = getIntent().getStringExtra("password");
        fullPhoneNumberS = getIntent().getStringExtra("fullPhoneNumber");

        emailText.setText(emailS);
        phoneText.setText(fullPhoneNumberS);
    }

    public void goToOTP (View view){
        Intent intent = new Intent(ResetPassword_selection_Screen.this, ResetPassword_OTP_Screen.class);
        intent.putExtra("fullPhoneNumber", fullPhoneNumberS);
        startActivity(intent);
    }

    public void goToEmailScreen (View view){
        Intent intent = new Intent(ResetPassword_selection_Screen.this, ResetPassword_EmailScreen.class);
        intent.putExtra("fullPhoneNumber", fullPhoneNumberS);
        intent.putExtra("email", emailS);
        startActivity(intent);
    }


    public void back(View view){
        super.onBackPressed();
    }

}