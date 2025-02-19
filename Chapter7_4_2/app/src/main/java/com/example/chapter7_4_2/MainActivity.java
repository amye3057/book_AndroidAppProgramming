package com.example.chapter7_4_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        // 변경된 부분 (LinearLayoutManager -> GridLayoutManager, 칼럼의 개수 설정해줌)

        recyclerView.setLayoutManager(layoutManager);
        PersonAdapter adapter = new PersonAdapter();

        adapter.addItem(new Person("강예지", "010-5555-5555"));
        adapter.addItem(new Person("강동원", "010-3737-3737"));
        adapter.addItem(new Person("강하늘", "010-1000-1000"));
        adapter.addItem(new Person("강감찬", "010-1111-1111"));
        adapter.addItem(new Person("강호동", "010-0000-0000"));
        adapter.addItem(new Person("강물", "010-0000-0000"));
        adapter.addItem(new Person("강바닥", "010-0000-0000"));
        adapter.addItem(new Person("강심장", "010-1010-1010"));
        adapter.addItem(new Person("강할강", "010-1000-1000"));
        adapter.addItem(new Person("강경한", "010-0000-0000"));
        adapter.addItem(new Person("강당", "010-0000-0000"));
        adapter.addItem(new Person("강성오", "010-1000-3057"));
        adapter.addItem(new Person("강현지", "010-2000-3057"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnPersonItemClickListener(){
            @Override
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {
                Person item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : "+item.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }
}