package com.hohimlee.mpa.MainScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hohimlee.mpa.Helper.FeaturedAdpater;
import com.hohimlee.mpa.Helper.FeaturedHelperClass;
import com.hohimlee.mpa.MainScreen.Running.Running_1;
import com.hohimlee.mpa.MainScreen.Running.others;
import com.hohimlee.mpa.R;

import java.util.ArrayList;


public class MainScreenFragment extends Fragment {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    Button running, cycling, swimming, others;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        featuredRecycler = view.findViewById(R.id.featured_recycler);
        featuredRecycler();

        running = (Button) view.findViewById(R.id.running);
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Running_1.class);
                intent.putExtra("event", "Running");
                startActivity(intent);
            }
        });

        cycling = (Button) view.findViewById(R.id.cycling);
        cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Running_1.class);
                intent.putExtra("event", "Cycling");
                startActivity(intent);
            }
        });

        swimming = (Button) view.findViewById(R.id.swimming);
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Running_1.class);
                intent.putExtra("event", "Swimming");
                startActivity(intent);
            }
        });

        others = (Button) view.findViewById(R.id.others);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.hohimlee.mpa.MainScreen.Running.others.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.confirmation_icon, "1111", "asdfasd sadfasdf asdfasdf sda fasdfasd fsda f sdaf ", 1));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.confirmation_icon, "2222", "asdfasd sadfasdf asdfasdf sda fasdfasd fsda f sdaf ",2));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.confirmation_icon, "3333", "asdfasd sadfasdf asdfasdf sda fasdfasd fsda f sdaf ", (float) 4.5));

        adapter = new FeaturedAdpater(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }
}