package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.hohimlee.mpa.Helper.CustomProgressBar;
import com.hohimlee.mpa.LoginAndSignUp.LoginSignUp;
import com.hohimlee.mpa.R;

public class ForgetPassword extends AppCompatActivity {

    Button continue_button;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        continue_button = findViewById(R.id.forgetPassword_Continue);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        phoneNumber = findViewById(R.id.phoneNumber);


    }


    public void goToRecoverScreen(View view){

        if (!validatePhoneNumber()){
            return;
        }
        CustomProgressBar progressBar = new CustomProgressBar(ForgetPassword.this);
        progressBar.show();

        String phoneNumberS = phoneNumber.getEditText().getText().toString().trim();
        if (phoneNumberS.charAt(0) == '0') {
            phoneNumberS = phoneNumberS.substring(1);
        }
        String fullPhoneNumberS ="+"+countryCodePicker.getFullNumber()+phoneNumberS;

        Query checkPhoneNumber = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(fullPhoneNumberS);

        checkPhoneNumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String emailS = snapshot.child(fullPhoneNumberS).child("email").getValue(String.class);
                    String passwordS = snapshot.child(fullPhoneNumberS).child("password").getValue(String.class);

                    Intent intent  = new Intent(getApplicationContext(), ResetPassword_OTP_Screen.class);
                    intent.putExtra("fullPhoneNumber", fullPhoneNumberS);
                    intent.putExtra("email", emailS);
                    intent.putExtra("password", passwordS);
                    progressBar.dismiss();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ForgetPassword.this, "No users with this phone number", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void back(View view){
        Intent intent = new Intent(ForgetPassword.this , LoginSignUp.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ForgetPassword.this , LoginSignUp.class);
        startActivity(intent);
        this.finish();
    }


}