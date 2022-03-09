package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hohimlee.mpa.Helper.CustomProgressBar;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;

public class ForgetPassword extends AppCompatActivity {

    Button continue_button;
    TextInputLayout email;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String emailS;
    CustomProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);
        progressBar = new CustomProgressBar(ForgetPassword.this);
        continue_button = findViewById(R.id.forgetPassword_Continue);
        email = findViewById(R.id.phoneNumber);
    }


    public void resetPassword(View view){
        progressBar.show();
        emailS = email.getEditText().getText().toString().trim();
        auth.sendPasswordResetEmail(emailS).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(ForgetPassword.this, EmailSentScreen.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ForgetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            }
        });

    }


    public void back(View view){
        Intent intent = new Intent(ForgetPassword.this , LoginScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ForgetPassword.this , LoginScreen.class);
        startActivity(intent);
        this.finish();
    }


}