package com.example.chapter11_3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText_DB, editText_TB;
    TextView textView;
    SQLiteDatabase database;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_DB = findViewById(R.id.editTextText);
        //editText_TB = findViewById(R.id.editTextText2);
        textView = findViewById(R.id.textView);

        Button button_DB = findViewById(R.id.button);
        button_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이터베이스 생성
                textView.append("데이터베이스 & 테이블 생성 시작\n");
                String dbName = editText_DB.getText().toString();

                // 바뀐 코드
                dbHelper = new DatabaseHelper(getApplicationContext(), dbName);
                database = dbHelper.getWritableDatabase();

                // database = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                textView.append("데이터베이스 & 테이블 생성 완료" + dbName + "\n");

                // 레코드 추가
                textView.append("레코드 추가 시작\n");

                database.execSQL("insert into " + "emp" + "(name, age, mobile)"+
                        "values" + "('Sheldon', 26, '010-3141-5926')");
                database.execSQL("insert into " + "emp" + "(name, age, mobile)"+
                        "values" + "('Penny', 26, '010-3141-5926')");
                database.execSQL("insert into " + "emp" + "(name, age, mobile)"+
                        "values" + "('Leonard', 26, '010-3141-5926')");
                textView.append("레코드 추가 완료\n");
            }
        });

        /*
        Button button_TB = findViewById(R.id.button2);
        button_TB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String tbName = editText_TB.getText().toString();

                // 레코드 추가
                textView.append("레코드 추가 시작\n");
                database.execSQL("insert into " + "emp" + "(name, age, mobile)"+
                        "values" + "('Sheldon', 26, 010-3141-5926)");
                textView.append("레코드 추가 완료\n");
            }
        });*/

        Button button_CK = findViewById(R.id.button3);
        button_CK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.append("데이터 호출 시작\n");

                Cursor cursor = database.rawQuery("select _id, name, age, mobile from emp", null);
                int recordCount = cursor.getCount();
                textView.append("레코드 개수 : "+recordCount+"\n");

                for(int i=0;i<recordCount;i++){
                    cursor.moveToNext(); // while문을 사용할 경우 moveToNext가 false를 반환할 때까지 반복하는 형식으로 사용.

                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int age = cursor.getInt(2);
                    String mobile = cursor.getString(3);

                    textView.append("레코드 #"+i+" : "+id+", "+name+", "+age+", "+mobile+"\n");
                }
                cursor.close();
            }
        });
    }
}