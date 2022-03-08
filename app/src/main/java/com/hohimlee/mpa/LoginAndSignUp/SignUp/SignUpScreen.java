package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginOTP_screen;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;

public class SignUpScreen extends AppCompatActivity {

    ImageView backButton;
    Button next, login;
    TextView title;
    TextInputLayout firstName, lastName, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen);

        backButton = findViewById(R.id.signUp_back_button);
        next = findViewById(R.id.signUp_next_button);
        login = findViewById(R.id.signUp_login_button);
        title = findViewById(R.id.signUp_title);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void nextSignUpScreen(View view) {

        if(!validateFirstName() | !validateLastName() | !validateEmail()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUpPasswordScreen.class);

        String firstNameS = firstName.getEditText().getText().toString().trim();
        String lastNameS = lastName.getEditText().getText().toString().trim();
        String emailS = email.getEditText().getText().toString().trim();

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference userRef  = firebase.getReference("Users");
        userRef.orderByChild("email").equalTo(emailS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(SignUpScreen.this, "Email already exits", Toast.LENGTH_SHORT).show();
                }
                else{
                    intent.putExtra("FirstName", firstNameS);
                    intent.putExtra("lastName", lastNameS);
                    intent.putExtra("email", emailS);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignUpScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFirstName() {
        String val = firstName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            firstName.setError("Cannot be empty");
            return false;
        } else {
            firstName.setError(null);
            firstName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName() {
        String val = lastName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            lastName.setError("Cannot be empty");
            return false;
        } else {
            lastName.setError(null);
            lastName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Cannot be empty");
            return false;

        } else if(!val.matches(checkEmail)){
            email.setError("Invalid Email");
            return false;
        }

        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();

        boolean has8char;
        boolean hasNumbers;
        boolean hasUpper;


        if(val.length()>= 8)
        {
            has8char = true;

        }else{
            has8char = false;
            password.setError("Password must have at least 8 characters");
        }

        if(val.matches("(.*[0-9].*)"))
        {
            hasNumbers = true;

        }else{
            hasNumbers = false;
            password.setError("Password must have at least one number");
        }

        if(val.matches("(.*[A-Z].*)"))
        {
            hasUpper = true;
        }else{
            hasUpper = false;
            password.setError("Password must have at least one uppercase letter");
        }

        if(has8char & hasNumbers & hasUpper){
            return true;
        }
        else{
            return false;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        this.finish();
    }

}