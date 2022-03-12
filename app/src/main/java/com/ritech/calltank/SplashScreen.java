package com.ritech.calltank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ritech.calltank.OnboardingScreens.Onboarding;
import com.ritech.calltank.OnboardingScreens.Screen3;

public class SplashScreen extends AppCompatActivity {

    final static int SPLASH_TIMER=2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this, Screen3.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);

    }
}