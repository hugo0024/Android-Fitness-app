package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.LoginAndSignUp.LoginSignUp;
import com.hohimlee.mpa.R;

public class SuccessScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_success_screen);

    }

    public void goToLogin (View view){
        Intent intent = new Intent(SuccessScreen.this, LoginScreen.class);
        startActivity(intent);
        this.finish();
    }


    public void back(View view){
        Intent intent = new Intent(SuccessScreen.this, LoginSignUp.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(SuccessScreen.this, LoginSignUp.class);
        startActivity(intent);
        this.finish();
    }

}