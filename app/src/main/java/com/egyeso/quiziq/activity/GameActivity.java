package com.egyeso.quiziq.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egyeso.quiziq.models.item;
import com.egyeso.quiziq.R;
import com.egyeso.quiziq.sqlite.databaseClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static final Random random = new Random();
    //views
    private Button b;
    private Button bb;
    private Button bbb;
    private Button bbbb;
    private Button bbbbb;
    private Button bbbbbb;
    private Button bbbbbbb;
    private Button btnTimer;
    private ImageView z;
    private ImageView zz;
    private ImageView zzz;
    private ImageView zzzz;
    private ImageView zzzzz;
    private String MsgEnd;
    private TextView txtFalse;
    private TextView txtTrue;

    private int rnd, id, sizeData, count = 30, count_b, point = 3;

    private final Handler handler = new Handler();
    private InterstitialAd mInterstitialAd;

    private List<item> mDataList;

    private final Runnable run = new Runnable() {
        @Override
        public void run() {
            timer();
        }
    };

    private MediaPlayer media_false;
    private MediaPlayer media_true;
    private MediaPlayer song;
    private CheckBox checkBox_vol;
    private Button check_music;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue_grey_500));
        }


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8419407261151146/2357763973");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                timer();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        b = findViewById(R.id.Qust);
        bb = findViewById(R.id.ans1);
        bbb = findViewById(R.id.ans2);
        bbbb = findViewById(R.id.ans3);
        bbbbb = findViewById(R.id.ans4);
        bbbbbb = findViewById(R.id.TotalQust);
        bbbbbbb = findViewById(R.id.addPointsBtn);
        btnTimer = findViewById(R.id.TimerBtn);

        zzzzz = findViewById(R.id.zzzzz);
        zzzz = findViewById(R.id.zzzz);
        zzz = findViewById(R.id.zzz);
        zz = findViewById(R.id.zz);
        z = findViewById(R.id.z);

        txtFalse = findViewById(R.id.txtFalse);
        txtTrue = findViewById(R.id.txtTrue);

        media_true = MediaPlayer.create(this, R.raw.sound_true);
        media_false = MediaPlayer.create(this, R.raw.sound_false);
        song = MediaPlayer.create(this, R.raw.music);

        checkBox_vol = findViewById(R.id.box_vol);
        check_music = findViewById(R.id.box_music);

        databaseClass mdata = new databaseClass(this);
        Bundle b = getIntent().getExtras();
        boolean rtn = false;
        if (b != null) {
            rtn = b.getBoolean("rtn");
        }

        File database = getApplicationContext().getDatabasePath(databaseClass.DBNAME);
        if (!database.exists()) {
            mdata.getReadableDatabase();
            //Copy db
            if (copyDatabase(this)) {
                Toast.makeText(this, "نسخ قاعدة البيانات", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "تعدر نسخ قاعدة البيانات", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        mDataList = mdata.getListProduct();
        sizeData = mDataList.size();
        if (rtn) {
            clear_savechange();
        } else {
            LoadSating();
        }
        timer();
        if (mDataList.size() <= 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage
                    ("مبروووك  لقد أنهيت جميع المراحل بنجاح \n ا" +
                            "نتظر الاصدار القادم " +
                            "\n أوإلعب من جديد");

            builder.setPositiveButton("إعادة المحاولة", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    restarteGame();
                }
            });
            builder.setNegativeButton("أرسل التطبيق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    share();
                }
            });
            builder.show();
            bbbbbb.setText(sizeData + "/" + sizeData);
            handler.removeCallbacks(run);
        } else {
            table();
            bbbbbb.setText(txtTrue.getText().toString() + "/" + sizeData);
            bbbbbbb.setText("أضف نقاط : " + point);
        }
    }

    @SuppressLint("SetTextI18n")
    private void timer() {
        handler.postDelayed(run, 1000);
        btnTimer.setText(count + "");
        count--;
        if (count == 25) {
            z.setVisibility(View.VISIBLE);
            zz.setVisibility(View.GONE);
            zzz.setVisibility(View.GONE);
            zzzz.setVisibility(View.GONE);
            zzzzz.setVisibility(View.GONE);

        }
        if (count == 20) {
            z.setVisibility(View.GONE);
            zz.setVisibility(View.VISIBLE);
            zzz.setVisibility(View.GONE);
            zzzz.setVisibility(View.GONE);
            zzzzz.setVisibility(View.GONE);

        }
        if (count == 15) {
            z.setVisibility(View.GONE);
            zz.setVisibility(View.GONE);
            zzz.setVisibility(View.VISIBLE);
            zzzz.setVisibility(View.GONE);
            zzzzz.setVisibility(View.GONE);
        }
        if (count == 10) {
            z.setVisibility(View.GONE);
            zz.setVisibility(View.GONE);
            zzz.setVisibility(View.GONE);
            zzzz.setVisibility(View.VISIBLE);
            zzzzz.setVisibility(View.GONE);

        }
        if (count == 5) {
            z.setVisibility(View.GONE);
            zz.setVisibility(View.GONE);
            zzz.setVisibility(View.GONE);
            zzzz.setVisibility(View.GONE);
            zzzzz.setVisibility(View.VISIBLE);

        }

        if (mDataList.size() <= 1) {
            handler.removeCallbacks(run);
        } else {
            if (count == 0) {
                table();
                count = 25;
                z.setVisibility(View.VISIBLE);

            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void addpoint(View viewAdd) {
        MediaPlayer mediaaddpoint = MediaPlayer.create(this, R.raw.sound_click);
        mediaaddpoint.start();

        if (point == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("لم يتبقى أي نقاط \n يمكنك مشاهدة إعلان  أو مشاركة البرنامج للحصول على النقاط");
            builder.setPositiveButton("إضافة نقاط", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    addpoint();
                }
            });
            builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            handler.removeCallbacks(run);
            count = 30;
            point--;
            bbbbbbb.setText(""+point);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("الإجابة الصحيح : \n رقم: " + count_b);
            builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            builder.show();
        }
    }

    private void addpoint() {
        Intent addPoin = new Intent(this, addPointActivity.class);
        startActivity(addPoin);
    }

    private void table() {

        handler.removeCallbacks(run);
        count = 25;
        rnd = random.nextInt(mDataList.size());
        id = mDataList.get(rnd).ID;
        b.setText(mDataList.get(rnd).Question);
        bb.setText(mDataList.get(rnd).Answer_1);
        bbb.setText(mDataList.get(rnd).Answer_2);
        bbbb.setText(mDataList.get(rnd).Answer_3);
        bbbbb.setText(mDataList.get(rnd).Answer_4);
        count_b = mDataList.get(rnd).ID_answer;
        timer();
        if (mInterstitialAd.isLoaded() & rnd == 2 || rnd == 4 || rnd == 8 || rnd == 16 || rnd == 32 || rnd == 64 || rnd == 128) {
            handler.removeCallbacks(run);
            mInterstitialAd.show();
        }
    }

    private boolean copyDatabase(Context context) {

        try {
            InputStream inputStream = context.getAssets().open(databaseClass.DBNAME);
            String outFileName = databaseClass.myPath + databaseClass.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void music(View view) {
        try {

            if (song.isPlaying()) {
                song.pause();
                check_music.setBackgroundResource(R.drawable.ic_notifications_off_black_24dp);
                song.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();

                    }
                });
                song.prepareAsync();

            } else {
                song.start();
                song.setLooping(true);
                check_music.setBackgroundResource(R.drawable.ic_notifications_black_24dp);

            }
//

        }catch (Exception ignored){

        }



    }

    public void ans1(View viewAns1) {
        if (count_b == 1) {
            BtnTrue();
        } else {
            BtnFalse();
        }
    }

    public void ans2(View view) {
        if (count_b == 2) {
            BtnTrue();
        } else {
            BtnFalse();
        }
    }

    public void ans3(View view) {
        if (count_b == 3) {
            BtnTrue();
        } else {
            BtnFalse();
        }
    }

    public void ans4(View view) {
        if (count_b == 4) {
            BtnTrue();
        } else {
            BtnFalse();
        }
    }

    @SuppressLint("SetTextI18n")
    private void BtnTrue() {

        LayoutInflater inflater = getLayoutInflater();
        View Toastview = inflater.inflate(R.layout.my_true_toast, (ViewGroup) findViewById(R.id.cusum_layout));
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 90);
        toast.setView(Toastview);
        toast.show();

        if (!checkBox_vol.isChecked()) {
            media_true.start(); //mute sound
        }


        int m = Integer.valueOf(txtTrue.getText().toString()) + 1;
        txtTrue.setText("" + m);
        bbbbbb.setText(m + "/" + sizeData);

        if (mDataList.size() <= 1) {
            finalMessage();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(MsgEnd +
                    "عدد الاجوبة الصحيحة: " + txtTrue.getText().toString() + "\n" +
                    "عدد الأجوبة الخاطئة: " + txtFalse.getText().toString());
            builder.setPositiveButton("إعادة المحاولة", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    restarteGame();
                }
            });
            builder.setNegativeButton("أرسل التطبيق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    share();
                }
            });
            builder.show();
            handler.postDelayed(run, 1000);
        } else {
            SaveSating(); ////"حفظ التغييرات ////////
            mDataList.remove(rnd);
            table();
        }
    }

    @SuppressLint("SetTextI18n")
    private void BtnFalse() {
        LayoutInflater inflater = getLayoutInflater();
        View Toastview2 = inflater.inflate(R.layout.my_wrong_toast, (ViewGroup) findViewById(R.id.cutoum_layout2));
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 90);
        toast.setView(Toastview2);
        toast.show();

        if (!checkBox_vol.isChecked()) {
            media_false.start();
        }
        int m = Integer.valueOf(txtFalse.getText().toString()) + 1;
        txtFalse.setText("" + m);

        if (mDataList.size() <= 1) {
            finalMessage();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("الجوب الصحيح هو: " + count_b + "\n" + MsgEnd +
                    "عدد الاجوبة الصحيحة: " + txtTrue.getText().toString() + "\n" +
                    "عدد الأجوبة الخاطئة: " + txtFalse.getText().toString());
            builder.setPositiveButton("إعادة المحاولة", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    restarteGame();
                }
            });
            builder.setNegativeButton("أرسل التطبيق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    share();
                }
            });
            builder.show();
            handler.postDelayed(run, 1000);
        } else {
            id = -1; /////// هذا الرقم لكي لا يتم تخزين قيمة موجودة لان الجواب خطأ ////////
            SaveSating(); ////"حفظ التغييرات ////////
            table();
        }

    }

    private void restarteGame() {
        Intent mainactivity = new Intent(this, MainActivity.class);
        startActivity(mainactivity);
    }

    private void finalMessage() {
        int txttrue = Integer.valueOf(txtTrue.getText().toString());
        int txtfalse = Integer.valueOf(txtFalse.getText().toString());

        if (txttrue > txtfalse) {
            MsgEnd = "رائع لقد أنهيت المراحل بشكل جيد. \n ";
        }
        if (txttrue <= txtfalse) {
            MsgEnd = "كنت ضعيف في الإجابة.\n ";
        }
    }

    private void share() {
        Intent myintent = new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");
        String body = " أسئلة وأجوبة ثقافية  \n" + "\n" +
                "https://play.google.com/store/apps/";

        Toast.makeText(this, getPackageName() + " ", Toast.LENGTH_SHORT).show();
        String sub = "أسئلة وأجوبة ثقافية \n";
        myintent.putExtra(Intent.EXTRA_SUBJECT, sub);
        myintent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(myintent, "مشاركة البرنامج"));
    }

    private void SaveSating() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savechange.edit();

        editor.putString("txtTrue", txtTrue.getText().toString());
        editor.putString("txtFalse", txtFalse.getText().toString());
        editor.putString("bbbbbb", bbbbbb.getText().toString());

        editor.putInt("Point", point);
        editor.putInt("list" + id, id);
        editor.apply();
    }

    private void LoadSating() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);

        String txttrue = savechange.getString("txtTrue", "0");
        txtTrue.setText(txttrue);
        String txtfalse = savechange.getString("txtFalse", "0");

        txtFalse.setText(txtfalse);
        String butn6 = savechange.getString("bbbbbb", sizeData + "/" + sizeData);
        bbbbbb.setText(butn6);

        this.point = savechange.getInt("Point", point);

        int i = 0;
        int data = mDataList.size();
        while (i < data) {
            try {
                int ii = 0;
                while (ii < data) {
                    int x = mDataList.get(ii).ID;
                    int listvale = savechange.getInt("list" + x, -1);
                    if (listvale == x) {
                        mDataList.remove(ii);
                    }
                    ii++;
                }
            } catch (Exception ignored) {
            }
            i++;
        }
    }

    private void clear_savechange() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savechange.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            song.pause();
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        table();

        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);
        this.point = savechange.getInt("Point", point);
        bbbbbbb.setText("" + point);
    }
}
