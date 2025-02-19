package com.example.chapter11_2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText_DB, editText_TB;
    TextView textView;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_DB = findViewById(R.id.editTextText);
        editText_TB = findViewById(R.id.editTextText2);
        textView = findViewById(R.id.textView);

        Button button_DB = findViewById(R.id.button);
        button_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이터베이스 생성
                textView.append("데이터베이스 생성 시작\n");
                String dbName = editText_DB.getText().toString();
                database = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                textView.append("데이터베이스 생성 완료" + dbName + "\n");
            }
        });

        Button button_TB = findViewById(R.id.button2);
        button_TB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 테이블 생성
                textView.append("테이블 생성 시작\n");
                String tbName = editText_TB.getText().toString();
                if(database==null){
                    textView.append("ERROR : 데이터베이스를 먼저 생성하세요.\n");
                    return;
                }

                database.execSQL("create table if not exists " + tbName + "(" +
                        "_id integer PRIMARY KEY autoincrement, " +
                        "name text, " + "age integer, " + "mobile text)");
                // 안드로이드에서 id는 _id로 만드는 것을 권장함.
                // PRIMARY KEY autoincrement : 자동으로 1씩 증가하는 키값
                // name, age, mobile 칼럼으로 정의하고 각각의 데이터 타입도 설정함.
                textView.append("테이블 생성 완료" + tbName + "\n");

                // 레코드 추가
                textView.append("레코드 추가 시작\n");
                database.execSQL("insert into " + tbName + "(name, age, mobile)"+
                        "values" + "('Sheldon', 26, 010-3141-5926)");
                textView.append("레코드 추가 완료\n");
            }
        });
    }
}