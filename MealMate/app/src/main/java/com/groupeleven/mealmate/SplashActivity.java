package com.groupeleven.mealmate;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView splashAnimation;
    RelativeLayout appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashAnimation = findViewById(R.id.animation);
        appName = findViewById(R.id.appNameMealMate);

        Window appWindow = this.getWindow();
        appWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        appWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        appWindow.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        splashAnimation.animate().translationY(0).setDuration(1000).setStartDelay(500);
        appName.animate().translationY(0).setDuration(1000).setStartDelay(1000);

        SharedPreferences sp = getSharedPreferences("MealMateSharedPreferences", MODE_PRIVATE);
        boolean isSignedIn = sp.getBoolean("isSignedIn", false);

        Intent nextActivityIntent;
        if (isSignedIn) {
            nextActivityIntent = new Intent(this, MainActivity.class);
        } else {
            nextActivityIntent = new Intent(this, LoginActivity.class);
        }
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                    startActivity(nextActivityIntent);
                    finish();
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }
}