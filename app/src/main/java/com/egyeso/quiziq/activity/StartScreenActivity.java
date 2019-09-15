package com.egyeso.quiziq.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.egyeso.quiziq.R;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                startActivity(new Intent(StartScreenActivity.this, MainActivity.class));
                finish();
            }
        };
        handler.postDelayed(r, 5000);
    }
}
