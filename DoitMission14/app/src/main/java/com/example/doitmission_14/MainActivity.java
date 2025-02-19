package com.example.doitmission_14;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        ShoppingAdapter adapter = new ShoppingAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new OnShoppingItemClickListener() {
            @Override
            public void onItemClick(ShoppingAdapter.ViewHolder holder, View view, int position) {
                ShoppingItem item = adapter.getItem(position);
                String n,p,d;
                n=item.getName().toString();
                p=item.getPrice().toString();
                d=item.getDescrpiton().toString();
                Toast.makeText(getApplicationContext(), "이름:"+n+", 가격:"+p+", 설명:"+d, Toast.LENGTH_LONG).show();
            }
        });

        adapter.addItem(new ShoppingItem("롱코트","160,000","명절 기획상품...",R.drawable.longcoat));
        adapter.addItem(new ShoppingItem("방탄 와이셔츠","80,000","특가상품...",R.drawable.shirt));
        adapter.addItem(new ShoppingItem("조깅화","220,000","반값상품...",R.drawable.shoes));
        adapter.addItem(new ShoppingItem("선글라스","500,000","비싼상품...",R.drawable.sunglasses));
        recyclerView.setAdapter(adapter);
    }
}