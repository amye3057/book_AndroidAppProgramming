package com.example.chapter11_5;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String uriString = "content://com.example.chapter11_5/person"; // 내용 제공자를 정의할 때 만든 Uri 값을 사용한다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button_insert = findViewById(R.id.button);
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append("insert 호출\n");
                Uri uri = new Uri.Builder().build().parse(uriString); // 문자열로 Uri 객체를 만드는 방법

                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                // Cursor 객체를 이용해 결과 값을 조회할 수 있음.
                String[] columns = cursor.getColumnNames(); // getColumnNames() : 레코드에 들어가 있는 칼럼의 이름을 조회하고 싶을 때 사용

                textView.append("columns count : " + columns.length + "\n");
                for(int i=0;i<columns.length;i++){
                    textView.append("#" + i + " : " + columns[i] + "\n");
                }

                ContentValues values = new ContentValues(); // ContentValues : 레코드를 추가할 때 사용되는 객체
                values.put("name", "Sheldon");
                values.put("age", 20);
                values.put("mobile", "010-3141-5926");

                uri = getContentResolver().insert(uri, values); // insert()에 Uri 객체, ContentValues 객체를 파라미터로 전달.
                textView.append("insert 결과 : " + uri.toString() + "\n");
            }
        });

        Button button_query = findViewById(R.id.button2);
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append("query 호출\n");
                try{
                    Uri uri = new Uri.Builder().build().parse(uriString);
                    String[] columns = new String[] {"name","age","mobile"}; // 조회할 칼럼의 이름이 문자열 배열 형태로 들어가 있음.
                    // 지정된 칼럼만 조회된다. (name, age, mobile)
                    Cursor cursor = getContentResolver().query(uri, columns, null, null, "name ASC");
                    textView.append("query 결과 : " + cursor.getCount() + "\n");

                    int index = 0;
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(columns[0]));
                        int age = cursor.getInt(cursor.getColumnIndexOrThrow(columns[1]));
                        String mobile = cursor.getString(cursor.getColumnIndexOrThrow(columns[2]));

                        textView.append("#" + index + " : " + name + ", " + age + ", " + mobile + "\n");
                        index++;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button button_update = findViewById(R.id.button3);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append("update 호출\n");

                Uri uri = new Uri.Builder().build().parse(uriString);

                String selection = "mobile = ?"; // ?는 selectionArgs의 첫번째 원소로 대체된다.
                String [] selectionArgs = new String[] {"010-3141-5926"}; // where 조건 : mobile = 010-3141-5926
                ContentValues updateValue = new ContentValues();
                updateValue.put("mobile", "010-9999-9999");
                // mobile 칼럼의 값이 010-3141-5926인 레코드만 010-9999-9999로 수정한다.

                int count = getContentResolver().update(uri, updateValue, selection, selectionArgs);
                // 파라미터 : Uri 객체 / ContentValues 객체 / where 조건 / where 조건의 ? 기호를 대체할 값
                textView.append("update 결과 : " + count + "\n");
            }
        });

        Button button_delete = findViewById(R.id.button4);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append("delete 호출\n");

                Uri uri = new Uri.Builder().build().parse(uriString);

                String selection = "name = ?";
                String[] selectionArgs = new String[] {"Sheldon"}; // where 조건 : name = sheldon

                int count = getContentResolver().delete(uri, selection, selectionArgs);
                textView.append("delete 결과 : " + count + "\n");
            }
        });
    }
}