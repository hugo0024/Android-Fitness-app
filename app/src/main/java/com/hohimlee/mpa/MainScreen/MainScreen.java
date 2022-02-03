package com.hohimlee.mpa.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.hohimlee.mpa.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainScreen extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen);
        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainScreenFragment()).commit();
        bottomMenu();
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
                        fragment = new MainScreenManage();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new MainScreenProfile();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

    public void onBackPressed()
    {
        return;
    }

}