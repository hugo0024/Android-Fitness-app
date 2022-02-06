package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hohimlee.mpa.R;

import java.security.MessageDigest;

public class NewPasswordScreen extends AppCompatActivity {

    TextInputLayout newPassword, confirmPassword;
    Button loginButton;
    String newPasswordS;
    String confirmPasswordS;
    String fullPhoneNumberS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_password_screen);

        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        loginButton = findViewById(R.id.newPassword_loginButton);

        fullPhoneNumberS = getIntent().getStringExtra("fullPhoneNumber");

    }

    public void goToDashboard (View view){

        newPasswordS = newPassword.getEditText().getText().toString().trim();
        confirmPasswordS = confirmPassword.getEditText().getText().toString().trim();

        if(!validatePassword()){
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        String hashPassword = sha256(newPasswordS);
        ref.child(fullPhoneNumberS).child("password").setValue(hashPassword);
        Intent intent = new Intent(NewPasswordScreen.this, SuccessScreen.class);
        startActivity(intent);
        this.finish();

    }

    private boolean validatePassword() {

        boolean has8char;
        boolean hasNumbers;
        boolean hasUpper;
        boolean passwordMatch;


        if(newPasswordS.length()>= 8)
        {
            has8char = true;
        }else{
            has8char = false;
            Toast.makeText(this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show();
        }

        if(newPasswordS.matches("(.*[0-9].*)"))
        {
            hasNumbers = true;

        }else{
            hasNumbers = false;
            Toast.makeText(this, "Password should contain a number", Toast.LENGTH_SHORT).show();
        }

        if(newPasswordS.matches("(.*[A-Z].*)"))
        {
            hasUpper = true;
        }
        else{
            hasUpper = false;
            Toast.makeText(this, "Password should contain a uppercase letter", Toast.LENGTH_SHORT).show();
        }
        if(newPasswordS.equals(confirmPasswordS)){
            passwordMatch = true;
        }
        else {
            passwordMatch = false;
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }

        if(has8char & hasNumbers & hasUpper & passwordMatch){
            return true;
        }
        else{
            return false;
        }
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void back(View view){
        Intent intent = new Intent(NewPasswordScreen.this, ForgetPassword.class);
        startActivity(intent);
        this.finish();
    }

    public void exit(View view){
        Intent intent = new Intent(NewPasswordScreen.this, ForgetPassword.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(NewPasswordScreen.this , ForgetPassword.class);
        startActivity(intent);
        this.finish();
    }

}