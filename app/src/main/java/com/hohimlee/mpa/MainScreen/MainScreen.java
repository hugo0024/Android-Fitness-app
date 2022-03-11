package com.hohimlee.mpa.MainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainScreen extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    String firstNameS, lastNameS, emailS, genderS, dateOfBirthS, phoneNumberS, signUpDateS;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen);
        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainScreenFragment()).commit();
        bottomMenu();

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        sp = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstNameS = (String) snapshot.child("firstName").getValue();
                lastNameS = (String) snapshot.child("lastName").getValue();
                emailS = (String) snapshot.child("email").getValue();
                genderS = (String) snapshot.child("gender").getValue();
                dateOfBirthS = (String) snapshot.child("dateOfBirth").getValue();
                phoneNumberS = (String) snapshot.child("phoneNumber").getValue();
                signUpDateS = (String) snapshot.child("signUpDate").getValue();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("firstName", firstNameS);
                editor.putString("lastName", lastNameS);
                editor.putString("email", emailS);
                editor.putString("gender", genderS);
                editor.putString("dateOfBirth", dateOfBirthS);
                editor.putString("phoneNumber", phoneNumberS);
                editor.putString("signUpDate", signUpDateS);
                editor.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                Intent intent = new Intent(MainScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_dashboard:
                        fragment = new MainScreenFragment();
                        break;
                    case R.id.bottom_nav_manage:
                        fragment = new MainScreenHistory();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new MainScreenProfile();
                        break;
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();//.replace(R.id.fragment_container, fragment);
                //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                //ft.setCustomAnimations(R.anim.exit_to_right, R.anim.enter_from_right);
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        });
    }

    public void onBackPressed()
    {
        return;
    }
}