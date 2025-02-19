package com.example.chapter7_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Layout1 layout1 = findViewById(R.id.layout1);

        layout1.setImage(R.drawable.ic_launcher_foreground);
        layout1.setName("강예지");
        layout1.setMoblie("010-1234-5678");

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setImage(R.drawable.images);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setImage(R.drawable.images2);
            }
        });
    }
}