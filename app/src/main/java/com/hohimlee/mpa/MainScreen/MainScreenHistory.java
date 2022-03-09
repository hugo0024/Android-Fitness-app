package com.hohimlee.mpa.MainScreen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hohimlee.mpa.Helper.RecyclerViewInterface;
import com.hohimlee.mpa.Helper.RecyclerView_Adapter;
import com.hohimlee.mpa.Helper.RecyclerView_data_handler;
import com.hohimlee.mpa.MainScreen.Running.Running_1;
import com.hohimlee.mpa.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainScreenHistory extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RecyclerView_Adapter recyclerViewAdapter;
    ArrayList<RecyclerView_data_handler> list;
    Dialog dialog;

    ImageView img;
    TextView date, miles, route, duration, startTime, endTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen_manage, container, false);

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.show_details_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        Button cancel = dialog.findViewById(R.id.workout_details_close_btn);
        img = dialog.findViewById(R.id.workout_details_img);
        date = dialog.findViewById(R.id.workout_details_date);
        miles = dialog.findViewById(R.id.workout_details_miles);
        route = dialog.findViewById(R.id.workout_details_route);
        duration = dialog.findViewById(R.id.workout_details_duration);
        startTime = dialog.findViewById(R.id.workout_details_startTime);
        endTime = dialog.findViewById(R.id.workout_details_endTime);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        recyclerView = view.findViewById(R.id.history_recycler);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Workout");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        recyclerViewAdapter = new RecyclerView_Adapter(getActivity(),list, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RecyclerView_data_handler handler = dataSnapshot.getValue(RecyclerView_data_handler.class);
                    list.add(handler);
                }
                Collections.reverse(list);
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(int position) {
        if(list.get(position).getEvent().equals("Running")){
            img.setImageResource(R.drawable.running_icon);
        }
        else if(list.get(position).getEvent().equals("Cycling")){
            img.setImageResource(R.drawable.cycling_icon);
        }

        date.setText(list.get(position).getDate());
        miles.setText(list.get(position).getMiles() + " miles");
        route.setText("At " + list.get(position).getRoute());
        duration.setText(list.get(position).getDuration());
        startTime.setText("Start at " + list.get(position).getStartTime());
        endTime.setText("End at " + list.get(position).getEndTime());
        dialog.show();

    }
}