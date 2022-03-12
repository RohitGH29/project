package com.ritech.calltank.OnboardingScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ritech.calltank.R;

public class Onboarding extends AppCompatActivity {

    private ViewPager viewPager;
    private OnboardingAdapter onboardingAdapterl;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        getSupportActionBar().hide();


        viewPager = findViewById(R.id.pageView);

        onboardingAdapterl = new OnboardingAdapter(this);

        viewPager.setAdapter(onboardingAdapterl);

        viewPager.addOnPageChangeListener(viewListner);

    }


    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
           // mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}