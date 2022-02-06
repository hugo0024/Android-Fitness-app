package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.LoginAndSignUp.Login.SignInWithEmail;
import com.hohimlee.mpa.LoginAndSignUp.ResetPassword.SuccessScreen;
import com.hohimlee.mpa.R;

public class EmailSentScreen extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    String emailS;
    String passwordS;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_email_sent_screen);

        emailS = getIntent().getStringExtra("email");
        passwordS = getIntent().getStringExtra("password");

        signInUser();
    }

    private void signInUser(){
        auth.signInWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendVerificationEmail();
                }
                else {
                    Toast.makeText(EmailSentScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();

                }
                else {
                    Toast.makeText(EmailSentScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
            }
        });
    }


    public void goToLogin (View view){
        Intent intent = new Intent(EmailSentScreen.this, SignInWithEmail.class);
        startActivity(intent);
        this.finish();
    }

    public void back(View view){
        Intent intent = new Intent(EmailSentScreen.this, LoginScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(EmailSentScreen.this, LoginScreen.class);
        startActivity(intent);
        this.finish();
    }
}