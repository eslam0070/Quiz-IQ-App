package com.egyeso.quiziq.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.egyeso.quiziq.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.green));
        }

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this,"ca-app-pub-8419407261151146/2357763973");
    }


    public void Play(View view) {
        Intent windows_asila = new Intent(this, GameActivity.class);
        windows_asila.putExtra("rtn",false);
        startActivity(windows_asila);
    }

    public void addpoint(View view) {
        Intent intent = new Intent(MainActivity.this,addPointActivity.class);
        startActivity(intent);
    }

    public void google(View view) {
        String url = "https://play.google.com/store/apps/developer?id=Eslam+Gamal";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void rating(View view) {
        Uri uri2 = Uri.parse("https://play.google.com/store/apps/details?id=com.egyeso.quiziq");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri2);
        startActivity(intent);
    }
}
