package com.hohimlee.mpa.MainScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hohimlee.mpa.Helper.RunningAdapter;
import com.hohimlee.mpa.Helper.RunningDataHandler;
import com.hohimlee.mpa.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainScreenManage extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RunningAdapter runningAdapter;
    ArrayList<RunningDataHandler> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen_manage, container, false);

        recyclerView = view.findViewById(R.id.history_recycler);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Workout");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        runningAdapter = new RunningAdapter(getActivity(),list);
        recyclerView.setAdapter(runningAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RunningDataHandler user = dataSnapshot.getValue(RunningDataHandler.class);
                    list.add(user);
                }
                Collections.reverse(list);
                runningAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}