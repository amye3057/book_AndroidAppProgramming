package com.example.doitmission_20;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;


public class MainActivity extends AppCompatActivity {
    String url;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        rssAdapter adapter = new rssAdapter();
        recyclerView.setAdapter(adapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="https://www.mk.co.kr/rss/30000001/";
                XMLParsing xp = new XMLParsing(url);
                adapter.setItems(xp.getData());
                adapter.notifyDataSetChanged(); // 리싸이클러뷰 갱신
            }
        });
    }
}