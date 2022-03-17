package com.example.drugstoremanagement.ui.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.ui.main.MainActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    ImageView imgSplash;
    TextView txt1;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgSplash = findViewById(R.id.img_splash);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);

        imgSplash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        txt1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left));
        txt2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_right));
        txt1.postDelayed(() -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            try {
                executor.submit(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            imgSplash.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));
            txt1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_to_right));
            txt2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_to_left));
            txt1.postDelayed(() -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }, 700);
        }, 700);



    }
}