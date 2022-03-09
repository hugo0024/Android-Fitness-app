package com.hohimlee.mpa.LoginAndSignUp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.hohimlee.mpa.Helper.CustomProgressBar;
import com.hohimlee.mpa.Helper.UserDataHandler;
import com.hohimlee.mpa.LoginAndSignUp.ResetPassword.ForgetPassword;
import com.hohimlee.mpa.LoginAndSignUp.SignUp.SignUpScreen;
import com.hohimlee.mpa.LoginAndSignUp.SignUp.phoneOTP;
import com.hohimlee.mpa.MainScreen.MainScreen;
import com.hohimlee.mpa.R;
import com.hohimlee.mpa.SplashScreen.introduction;

import java.security.MessageDigest;
import java.sql.Array;
import java.util.Arrays;


import javax.security.auth.callback.Callback;

import static android.content.ContentValues.TAG;

public class LoginScreen extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    GoogleSignInClient mGoogleSignInClient;
    String googleEmailS,firstNameS, lastNameS, genderS, dobS, phoneNumberS,EmailS;
    CallbackManager callbackManager;
    CustomProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
            startActivity(intent);
            finish();
        }

        progressBar = new CustomProgressBar(LoginScreen.this);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);
        countryCodePicker = findViewById(R.id.login_countryCodePicker);
        phoneNumber = findViewById(R.id.login_phoneNumber);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("780220218443-nhgqem79c548qfh13npcksaqtaoasp28.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void SignInWithFacebook(View view){
        progressBar.show();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        progressBar.dismiss();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginScreen.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            EmailS = user.getEmail();
                            UserDataHandler addNewUser = new UserDataHandler(firstNameS, lastNameS, EmailS, genderS, dobS, phoneNumberS);
                            FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                            DatabaseReference userRef  = firebase.getReference("Users");
                            userRef.child(user.getUid()).setValue(addNewUser);
                            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                    }
                    }
                });
    }


    public void googleSingIn(View view){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
        progressBar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                EmailS = account.getEmail();
                firstNameS = account.getFamilyName();
                lastNameS = account.getGivenName();

                FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                DatabaseReference userRef  = firebase.getReference("Users");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userRef.orderByChild("email").equalTo(googleEmailS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            Toast.makeText(LoginScreen.this, "Account already exits, please use your original sign-in method", Toast.LENGTH_LONG).show();
                            mGoogleSignInClient.signOut();
                            progressBar.dismiss();

                        }
                        else{
                            firebaseAuthWithGoogle(account.getIdToken());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }
                });

            } catch (ApiException e) {
                progressBar.dismiss();
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            progressBar.dismiss();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                            DatabaseReference userRef  = firebase.getReference("Users");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            userRef.orderByChild("email").equalTo(googleEmailS).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                                        startActivity(intent);
                                        finish();
                                        progressBar.dismiss();
                                    }
                                    else{
                                        UserDataHandler addNewUser = new UserDataHandler(firstNameS, lastNameS, EmailS, genderS, dobS, phoneNumberS);
                                        userRef.child(user.getUid()).setValue(addNewUser);
                                        Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                                        startActivity(intent);
                                        finish();
                                        progressBar.dismiss();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(LoginScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        return;
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


        progressBar.show();

        String phoneNumberS = phoneNumber.getEditText().getText().toString().trim();
        if (phoneNumberS.charAt(0) == '0') {
            phoneNumberS = phoneNumberS.substring(1);
        }
        String fullPhoneNumberS = "+" + countryCodePicker.getFullNumber() + phoneNumberS;

/*      firebaseAuth.signInWithEmailAndPassword(phoneNumberS, oldPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginScreen.this, "good", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    checkIfEmailVerified();
                }
                else {
                    Toast.makeText(LoginScreen.this, "phoneNumberS", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            }
        });*/
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference  userRef  = firebase.getReference("Users");
        userRef.orderByChild("phoneNumber").equalTo(fullPhoneNumberS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Intent intent  = new Intent(getApplicationContext(), LoginOTP_screen.class);
                    intent.putExtra("fullPhoneNumber", fullPhoneNumberS);
                    startActivity(intent);
                    progressBar.dismiss();
                    finish();
                }
                else{
                    progressBar.dismiss();
                    Toast.makeText(LoginScreen.this, "Phone number not exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.dismiss();
                Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

/*
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
        });*/
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
        Intent intent = new Intent(LoginScreen.this, SignInWithEmail.class);
        startActivity(intent);

    }

    public void back(View view) {
        Intent intent = new Intent(LoginScreen.this, introduction.class);
        startActivity(intent);
        this.finish();
    }

}