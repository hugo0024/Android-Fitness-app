package com.hohimlee.mpa.LoginAndSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.LoginAndSignUp.SignUp.SignUpScreen;
import com.hohimlee.mpa.MainScreen.MainScreen;
import com.hohimlee.mpa.R;
import com.hohimlee.mpa.SplashScreen.introduction;

public class LoginSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_sign_up);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user != null){
            startActivity(new Intent(LoginSignUp.this, MainScreen.class));
        }

    }

    public void login_screen(View view){

        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
    }

    public void signUp_screen(View view){

        Intent intent = new Intent(getApplicationContext(), SignUpScreen.class);
        startActivity(intent);
    }

    public void back(View view){
        Intent intent = new Intent(getApplicationContext(), introduction.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        return;
    }

}