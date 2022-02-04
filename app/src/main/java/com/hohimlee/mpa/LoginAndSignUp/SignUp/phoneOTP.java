package com.hohimlee.mpa.LoginAndSignUp.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.MainScreen.MainScreen;
import com.hohimlee.mpa.R;
import com.hohimlee.mpa.Helper.UserDataHandler;

import java.util.concurrent.TimeUnit;

public class phoneOTP extends AppCompatActivity {

    PinView pin;
    String systemCode;
    TextView title;
    String firstNameS,lastNameS,emailS,passwordS,genderS,dateS,phoneNumberS,fullPhoneNumberS;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone_o_t_p);

        pin = findViewById(R.id.pin_view);

        firstNameS = getIntent().getStringExtra(("firstName"));
        lastNameS = getIntent().getStringExtra(("lastName"));
        emailS = getIntent().getStringExtra(("email"));
        passwordS = getIntent().getStringExtra(("password"));
        genderS = getIntent().getStringExtra(("gender"));
        dateS = getIntent().getStringExtra(("date"));
        phoneNumberS = getIntent().getStringExtra(("phoneNumberS"));
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
                    Toast.makeText(phoneOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemCode, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            storeData();
                            Toast.makeText(phoneOTP.this, "Completed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                            startActivity(intent);

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(phoneOTP.this, "Please Try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");
        UserDataHandler addNewUser = new UserDataHandler(firstNameS, lastNameS, emailS, passwordS, genderS, dateS, fullPhoneNumberS);
        reference.child(fullPhoneNumberS).setValue(addNewUser);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(emailS);
    }

    public void nextScreen(View view){
        String code = pin.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }


    public void exit(View view){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        this.finish();
    }
}

