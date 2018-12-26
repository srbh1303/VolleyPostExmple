package com.example.Agriculture.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.Agriculture.R;
import com.example.Agriculture.activity.MainActivity;

public class Splash_Screen extends AppCompatActivity {

    ImageView logo;
    Animation animation;
    int DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        logo = (ImageView) findViewById(R.id.splash_img_logo);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splashanimation);
        logo.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY);
    }
}
