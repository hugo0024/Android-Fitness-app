package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;

public class SignUpPasswordScreen extends AppCompatActivity {

    TextInputLayout password;
    TextInputEditText passwordText;
    CardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_password_screen);

        password = findViewById(R.id.password);
        passwordText = findViewById(R.id.password_exitText);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);

        inputChanged();
    }

    public void nextSignUpScreen(View view) {

        if(!validatePassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUpScreen_2.class);

        String firstNameS = getIntent().getStringExtra("FirstName");
        String lastNameS = getIntent().getStringExtra("lastName");
        String emailS = getIntent().getStringExtra("email");
        String passwordS = password.getEditText().getText().toString().trim();

        intent.putExtra("FirstName", firstNameS);
        intent.putExtra("lastName", lastNameS);
        intent.putExtra("email", emailS);
        intent.putExtra("password", passwordS);


        startActivity(intent);

    }

    private  void inputChanged(){
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @SuppressLint("ResourceType")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }



    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();

        boolean has8char;
        boolean hasNumbers;
        boolean hasUpper;


        if(val.length()>= 8)
        {
            has8char = true;
            card1.setCardBackgroundColor(Color.parseColor("#96D294"));

        }else{
            has8char = false;
            card1.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }

        if(val.matches("(.*[0-9].*)"))
        {
            hasNumbers = true;
            card2.setCardBackgroundColor(Color.parseColor("#96D294"));

        }else{
            hasNumbers = false;
            card2.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }

        if(val.matches("(.*[A-Z].*)"))
        {
            hasUpper = true;
            card3.setCardBackgroundColor(Color.parseColor("#96D294"));
        }
        else{
            hasUpper = false;
            card3.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }

        if(has8char & hasNumbers & hasUpper){
            return true;
        }
        else{
            return false;
        }
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
