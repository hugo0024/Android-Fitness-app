package com.hohimlee.mpa.LoginAndSignUp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;
import com.hohimlee.mpa.Helper.CustomProgressBar;
import com.hohimlee.mpa.LoginAndSignUp.ResetPassword.ForgetPassword;
import com.hohimlee.mpa.LoginAndSignUp.SignUp.EmailSentScreen;
import com.hohimlee.mpa.LoginAndSignUp.SignUp.SignUpScreen;
import com.hohimlee.mpa.MainScreen.MainScreen;
import com.hohimlee.mpa.R;
import com.hohimlee.mpa.SplashScreen.introduction;

public class SignInWithEmail extends AppCompatActivity {

    TextInputLayout email, password;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String emailS,passwordS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_with_email);

        email = findViewById(R.id.login_phoneNumber);
        password = findViewById(R.id.login_password);
    }

    public void Login(View view){

        emailS = email.getEditText().getText().toString().trim();
        passwordS = password.getEditText().getText().toString().trim();

        CustomProgressBar progressBar = new CustomProgressBar(SignInWithEmail.this);
        progressBar.show();

        firebaseAuth.signInWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.dismiss();
                    checkIfEmailVerified();
                }
                else {
                    Toast.makeText(SignInWithEmail.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            }
        });
    }

    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            Toast.makeText(SignInWithEmail.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
            startActivity(intent);
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, EmailSentScreen.class);
            intent.putExtra("email", emailS);
            intent.putExtra("password", passwordS);
            startActivity(intent);
        }
        this.finish();
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void gotToForgetPassword(View view){
        Intent intent = new Intent(SignInWithEmail.this, ForgetPassword.class);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(SignInWithEmail.this, LoginScreen.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignInWithEmail.this, LoginScreen.class);
        startActivity(intent);
        this.finish();
    }
}