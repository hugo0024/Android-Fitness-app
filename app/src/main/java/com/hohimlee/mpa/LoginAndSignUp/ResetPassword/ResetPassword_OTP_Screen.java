package com.hohimlee.mpa.LoginAndSignUp.ResetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hohimlee.mpa.R;

import java.util.concurrent.TimeUnit;

public class ResetPassword_OTP_Screen extends AppCompatActivity {


    PinView pin;
    String systemCode, fullPhoneNumberS;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password__o_t_p__screen);

        pin = findViewById(R.id.pin_view);
        fullPhoneNumberS = getIntent().getStringExtra(("fullPhoneNumber"));
        sendPin(fullPhoneNumberS);
    }

    private void sendPin(String fullPhoneNumberS) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(fullPhoneNumberS)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    systemCode = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if(code != null){
                        pin.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(ResetPassword_OTP_Screen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemCode, code);
        Toast.makeText(ResetPassword_OTP_Screen.this, "Verification successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), NewPasswordScreen.class);
        intent.putExtra("fullPhoneNumber", fullPhoneNumberS);
        startActivity(intent);
        finish();
    }



    public void nextScreen(View view){
        String code = pin.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ResetPassword_OTP_Screen.this , ForgetPassword.class);
        startActivity(intent);
        this.finish();
    }

    public void back(View view){
        Intent intent = new Intent(ResetPassword_OTP_Screen.this , ForgetPassword.class);
        startActivity(intent);
        this.finish();
    }
}