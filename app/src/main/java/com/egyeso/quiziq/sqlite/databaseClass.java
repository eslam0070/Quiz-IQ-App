package com.egyeso.quiziq.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.egyeso.quiziq.models.item;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SdCardPath")
public class databaseClass extends SQLiteOpenHelper {

    public static final String  myPath = "/data/data/com.egyeso.quiziq/databases/";
    public static final String DBNAME = "myDataBase.db";
    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public databaseClass (Context context) {
        super(context, DBNAME, null, 2);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }

    public List<item> getListProduct() {
        item product;
        List<item> productList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tablo", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product = new item(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6));
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return productList;
    }
}
