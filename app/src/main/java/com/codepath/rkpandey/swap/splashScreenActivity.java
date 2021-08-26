package com.codepath.rkpandey.swap;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class splashScreenActivity extends AppCompatActivity {


    private ImageView cinemaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        cinemaView = findViewById(R.id.cinemaIcon);
        //creating animation feature for cinemaIcon
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(cinemaView,"alpha",1,0.3f);
        fadeOut.setDuration(2000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(cinemaView,"alpha",0.3f,1);
        fadeIn.setDuration(2000);
        final AnimatorSet mAnimatorset = new AnimatorSet();
        mAnimatorset.play(fadeIn).after(fadeOut);
        mAnimatorset.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorset.start();
            }
        });
        mAnimatorset.start();

        //making the animation last 4 seconds and open the main activity
        new CountDownTimer(4000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(splashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        }.start();

    }
}