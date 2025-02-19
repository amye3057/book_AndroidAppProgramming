package com.example.chapter2mission;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewUp;
    ImageView imageViewDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        imageViewUp = findViewById(R.id.imageView5);
        imageViewDown = findViewById(R.id.imageView7);
    }

    public void button_up(View v){
        imageViewUp.setVisibility(View.VISIBLE);
        imageViewDown.setVisibility(View.INVISIBLE);
    }
    public void button_down(View v){
        imageViewDown.setVisibility(View.VISIBLE);
        imageViewUp.setVisibility(View.INVISIBLE);
    }
}