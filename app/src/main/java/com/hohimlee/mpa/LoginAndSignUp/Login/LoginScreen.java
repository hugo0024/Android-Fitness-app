package com.hohimlee.mpa.LoginAndSignUp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.hohimlee.mpa.Helper.CustomProgressBar;
import com.hohimlee.mpa.LoginAndSignUp.ResetPassword.ForgetPassword;
import com.hohimlee.mpa.LoginAndSignUp.SignUp.SignUpScreen;
import com.hohimlee.mpa.R;

public class LoginScreen extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        countryCodePicker = findViewById(R.id.login_countryCodePicker);
        phoneNumber = findViewById(R.id.login_phoneNumber);
        password = findViewById(R.id.login_password);
        CustomProgressBar progressBar = new CustomProgressBar(LoginScreen.this);
    }

    private void back(View view) {
        super.onBackPressed();
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpScreen.class);
        startActivity(intent);
        this.finish();
    }

    public void Login(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        CustomProgressBar progressBar = new CustomProgressBar(LoginScreen.this);
        progressBar.show();
        String phoneNumberS = phoneNumber.getEditText().getText().toString().trim();
        String passwordS = password.getEditText().getText().toString().trim();
        if (phoneNumberS.charAt(0) == '0') {
            phoneNumberS = phoneNumberS.substring(1);
        }
        String fullPhoneNumberS = "+" + countryCodePicker.getFullNumber() + phoneNumberS;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(fullPhoneNumberS);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String databasePassword = snapshot.child(fullPhoneNumberS).child("password").getValue(String.class);
                    if (databasePassword.equals(passwordS)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String firstNameS = snapshot.child(fullPhoneNumberS).child("firstName").getValue(String.class);
                        String lastNameS = snapshot.child(fullPhoneNumberS).child("lastName").getValue(String.class);
                        String genderS = snapshot.child(fullPhoneNumberS).child("gender").getValue(String.class);
                        String emailS = snapshot.child(fullPhoneNumberS).child("email").getValue(String.class);
                        String dataOfBirthS = snapshot.child(fullPhoneNumberS).child("dateOfBirth").getValue(String.class);

                        Intent intent  = new Intent(getApplicationContext(), LoginOTP_screen.class);
                        intent.putExtra("fullPhoneNumber", fullPhoneNumberS);
                        progressBar.dismiss();
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(LoginScreen.this, "Phone number and password does not match", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }
                }
                else{
                    Toast.makeText(LoginScreen.this, "No users with this phone number", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        });
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

    public void gotToForgetPassword(View view){
        Intent intent = new Intent(LoginScreen.this, ForgetPassword.class);
        startActivity(intent);

    }

}