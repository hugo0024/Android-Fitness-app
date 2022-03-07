package com.hohimlee.mpa.MainScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hohimlee.mpa.Helper.FeaturedAdpater;
import com.hohimlee.mpa.Helper.FeaturedHelperClass;
import com.hohimlee.mpa.R;

import java.util.ArrayList;


public class MainScreenFragment extends Fragment {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        featuredRecycler = view.findViewById(R.id.featured_recycler);
        featuredRecycler();

        return view;
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.card1workout, "1111", "asdfasd sadfasdf asdfasdf sda fasdfasd fsda f sdaf ", 1));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.card2workout, "2222", "asdfasd sadfasdf asdfasdf sda fasdfasd fsda f sdaf ",2));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.card3workout, "3333", "asdfasd sadfasdf asdfasdf sda fasdfasd fsda f sdaf ", (float) 4.5));

        adapter = new FeaturedAdpater(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }
}