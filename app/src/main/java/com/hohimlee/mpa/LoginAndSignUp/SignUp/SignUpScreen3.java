package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;

public class SignUpScreen3 extends AppCompatActivity {

    TextView title;
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference userRef  = firebase.getReference("Users");
        String finalPhoneNumberS = phoneNumberS;

        userRef.orderByChild("phoneNumber").equalTo(fullPhoneNumberS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(SignUpScreen3.this, "Phone number already exits", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent  = new Intent(getApplicationContext(), phoneOTP.class);

                    intent.putExtra("firstName", firstNameS);
                    intent.putExtra("lastName", lastNameS);
                    intent.putExtra("email", emailS);
                    intent.putExtra("password", passwordS);
                    intent.putExtra("gender", genderS);
                    intent.putExtra("date", dateS);
                    intent.putExtra("phoneNumber", finalPhoneNumberS);
                    intent.putExtra("fullPhoneNumber", fullPhoneNumberS);

                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignUpScreen3.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void back(View view){
        super.onBackPressed();
    }

    public void goToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        this.finish();
    }
}