package com.hohimlee.mpa.MainScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hohimlee.mpa.LoginAndSignUp.Login.LoginScreen;
import com.hohimlee.mpa.R;

public class MainScreenProfile extends Fragment {

    Button Logout_button;
    SharedPreferences sp;
    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    String firstNameS, lastNameS, emailS, genderS, dateOfBirthS, phoneNumberS;
    TextView t1,t2,t3,t4,t5,t6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen_profile, container, false);

        t1 = (TextView) view.findViewById(R.id.firstName);
        t2 = (TextView) view.findViewById(R.id.lastName);
        t3 = (TextView) view.findViewById(R.id.email);
        t4 = (TextView) view.findViewById(R.id.gender);
        t5 = (TextView) view.findViewById(R.id.dob);
        t6 = (TextView) view.findViewById(R.id.phone);

        sp = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        firstNameS = sp.getString("firstName", "");
        lastNameS = sp.getString("lastName", "");
        emailS = sp.getString("email", "");
        genderS = sp.getString("gender", "");
        dateOfBirthS = sp.getString("dateOfBirth", "");
        phoneNumberS = sp.getString("phoneNumber", "");

        t1.setText(firstNameS);
        t2.setText(lastNameS);
        t3.setText(emailS);
        t4.setText(genderS);
        t5.setText(dateOfBirthS);
        t6.setText(phoneNumberS);


        Logout_button = (Button) view.findViewById(R.id.logOut);
        Logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                AuthUI.getInstance().signOut(getActivity()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getActivity(), LoginScreen.class);
                        Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });

        return view;
    }

}