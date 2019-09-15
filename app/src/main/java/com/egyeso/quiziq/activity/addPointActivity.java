package com.egyeso.quiziq.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.egyeso.quiziq.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class addPointActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private int point = 5;
    private int rate = 0;
    private Button rateBtn;
    private Button faceBtn;
    private boolean FaceDone = true;
    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd mInterstitialAd;
    private ScheduledExecutorService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);

        rateBtn = findViewById(R.id.rateApp);
        faceBtn = findViewById(R.id.fac);


        LoadSating();


        preparAd();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }else{
                            Toast.makeText(addPointActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }preparAd();
                    }
                });
            }
        },5,5, TimeUnit.SECONDS);
        MobileAds.initialize(this,"ca-app-pub-8419407261151146/2357763973");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-8419407261151146/5909162020",new AdRequest.Builder().build());
    }

    private void preparAd(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8419407261151146/2357763973");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void RatingApp(View viewRating) {
        Uri uri2 = Uri.parse("https://play.google.com/store/apps/details?id=com.egyeso.quiziq");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri2);
        startActivity(intent);

        rate++;
        if (rate >= 5){
            rateBtn.setEnabled(false);
        }

        rateBtn.setEnabled(false);
        point = point+5;
        SaveSating();

    }

    @Override
    protected void onDestroy() {
        service.shutdown();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        service.shutdown();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        service.shutdown();
        super.onPause();
    }

    public void AdsMob(View view) {

        if (mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRewarded(RewardItem rewardItem) {
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    public void ShareApp(View viewShare) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String body = "شارك البرنامج مع أصدقائك للحصول على 5 نقطة وابدأ اللعبة : \n" + "\n" +
                "https://play.google.com/store/apps/details?id=com.egyeso.quiziq";
        String sub = "5 نقطة مقابل كل مشاركة للتطبيق \n";
        intent.putExtra(Intent.EXTRA_SUBJECT, sub);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "مشاركة البرنامج"));
        point = point+4 ;
        SaveSating();

        Toast.makeText(this, " شكرا لك  " + " لديك  " + point + " ", Toast.LENGTH_SHORT).show();
    }

    public void Facebook(View vieww) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String myApp = "https://play.google.com/store/apps/details?id=com.egyeso.quiziq";
        intent.putExtra(Intent.EXTRA_TEXT,myApp);
        intent.setPackage("com.facebook.katana");
        startActivity(intent);

        point = point+5;
        FaceDone = true;
        faceBtn.setEnabled(true);
        SaveSating();
    }


    private void SaveSating() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", 0);
        SharedPreferences.Editor editor = savechange.edit();

        editor.putInt("Point",point);
        editor.putInt("rate", rate);
        int share = 0;
        editor.putInt("share", share);
        editor.putBoolean("adds" , true);
        editor.putBoolean("fac" , FaceDone);
        editor.putBoolean("apps" , true);

        editor.apply();
    }


    private void LoadSating() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);

        point = savechange.getInt("Point",point);

    }

}
