package com.example.doitmission_21;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String db_name = "book.db";
    public static String tb_name = "book";
    public static int version = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + tb_name + "(" + "_id integer PRIMARY KEY autoincrement, "
                + "title text, " + "writer text, " + "story text)";
        sqLiteDatabase.execSQL(sql);
        Log.d("TAG", tb_name + " 테이블 생성 완료");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { // i : oldVersion, i1 : newVersion
        if(i1>1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tb_name);
        }
    }
}
