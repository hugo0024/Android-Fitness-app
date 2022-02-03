package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hohimlee.mpa.LoginAndSignUp.LoginSignUp;
import com.hohimlee.mpa.R;

public class ResetPassword_EmailScreen extends AppCompatActivity {

    String fullPhoneNumberS, emailS;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password__email_screen);

        fullPhoneNumberS = getIntent().getStringExtra("fullPhoneNumber");
        emailS = getIntent().getStringExtra("email");
        firebaseAuth = FirebaseAuth.getInstance();

        Toast.makeText(ResetPassword_EmailScreen.this, emailS, Toast.LENGTH_SHORT).show();
    }

    public void goToLogin (View view){

        firebaseAuth.sendPasswordResetEmail(emailS).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(ResetPassword_EmailScreen.this, "Email has been sent", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ResetPassword_EmailScreen.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void back(View view){
        Intent intent = new Intent(ResetPassword_EmailScreen.this, LoginSignUp.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ResetPassword_EmailScreen.this, LoginSignUp.class);
        startActivity(intent);
        this.finish();
    }
}