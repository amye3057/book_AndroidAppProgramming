package com.example.chapter11_3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //public static String name = "employee.db";
    public static String tbName = "emp";
    public static int version = 1;

    public DatabaseHelper(@Nullable Context context, String name) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + tbName + "(" + " _id integer PRIMARY KEY autoincrement, "
                + " name text, " + " age integer, " + " mobile text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>1){
            db.execSQL("DROP TABLE IF EXISTS "+tbName);
        }
    }
}
