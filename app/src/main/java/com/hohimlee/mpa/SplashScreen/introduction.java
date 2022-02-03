package com.hohimlee.mpa.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hohimlee.mpa.LoginAndSignUp.LoginSignUp;
import com.hohimlee.mpa.R;
import com.hohimlee.mpa.Helper.SliderAdapter;

public class introduction extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button getStartButton;
    Button getSkipButton;
    Button nextButton;
    Animation animation;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introduction);

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        getStartButton = findViewById(R.id.start_button);
        getSkipButton = findViewById(R.id.skip_button);
        nextButton = findViewById(R.id.next_button);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view){
        viewPager.setCurrentItem(currentPosition = 2);
    }

    public void start(View view){
        startActivity(new Intent(getApplicationContext(), LoginSignUp.class));
    }

    public  void next(View view){
        viewPager.setCurrentItem(currentPosition + 1);
    }


    private void addDots(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i ++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition = position;

            if(position == 0){
                getStartButton.setVisibility(View.INVISIBLE);
                getSkipButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }
            else if(position == 1){
                getStartButton.setVisibility(View.INVISIBLE);
                getSkipButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }
            else {
                animation = AnimationUtils.loadAnimation(introduction.this, R.anim.button_anim);
                getStartButton.setAnimation(animation);
                getStartButton.setVisibility(View.VISIBLE);
                getSkipButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}