package com.example.doitmission_21;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    DatabaseHelper helper;

    RecyclerView recyclerView;
    BookAdapter adapter;
    EditText editText_title, editText_writer, editText_story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout inputLayout, searchLayout;
        inputLayout = findViewById(R.id.InputLayout);
        searchLayout = findViewById(R.id.SearchLayout);
        recyclerView = findViewById(R.id.RecyclerView);
        //adapter = new BookAdapter();

        // 리싸이클러뷰 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setAdapter(adapter);

        //  데이터베이스 & 테이블 생성
        helper = new DatabaseHelper(getApplicationContext());
        database = helper.getWritableDatabase();

        //Cursor cursor = database.rawQuery("select title, writer, story from book", null);

        editText_title = findViewById(R.id.editTextText);
        editText_writer = findViewById(R.id.editTextText2);
        editText_story = findViewById(R.id.editTextText3);

        // 입력 페이지
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLayout.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.INVISIBLE);
            }
        });

        // 입력 페이지 - 저장
        Button button_save = findViewById(R.id.button3);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = editText_title.getText().toString();
                String w = editText_writer.getText().toString();
                String s = editText_story.getText().toString();
                Log.d("TAG", t+", "+w+", "+s);
                if(t.isEmpty()){
                    Toast.makeText(getApplicationContext(),"제목을 작성하세요.",Toast.LENGTH_LONG).show();
                }
                else if(w.isEmpty()){
                    Toast.makeText(getApplicationContext(),"저자를 작성하세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    // 레코드 생성
                    database.execSQL("insert into " + "book" + "(title, writer, story)"+ "values" + "('" + t +"', '" + w + "', '" + s +"')");
                    Toast.makeText(getApplicationContext(),t+":"+w+":"+s+" 저장되었습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 조회 페이지
        Button button_search = findViewById(R.id.button2);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 버튼 클릭 시 BookAdapter로 연결된 ArrayList에 값을 넣어준다.
                inputLayout.setVisibility(View.INVISIBLE);
                searchLayout.setVisibility(View.VISIBLE);

                adapter = new BookAdapter(); // 어댑터 초기화. 조회를 누를때마다.. 리싸이클러뷰를 다시 보여주는 식으로.

                 Cursor cursor = database.rawQuery("select title, writer, story from book", null);

                int recordCount = cursor.getCount();
                Log.d("TAG","recordCount : " + recordCount);

                if(cursor.getCount()>0){
                    while(cursor.moveToNext()){
                        BookItem bookItem = new BookItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                        Log.d("TAG","커서 : " + cursor.getString(0) + cursor.getString(1));
                        adapter.addItem(bookItem);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });
    }
}
