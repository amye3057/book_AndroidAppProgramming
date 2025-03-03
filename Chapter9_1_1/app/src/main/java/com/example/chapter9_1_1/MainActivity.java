package com.example.chapter9_1_1;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    Handler handler = new Handler(); // API의 기본 핸들러 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundThread thread = new BackgroundThread();
                thread.start();
            }
        });
    }

    class BackgroundThread extends Thread{
        int value = 0;

        public void run(){
            for(int i=0;i<100;i++){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){}

                value+=1;
                Log.d("Thread","value : "+value);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("value 값 : "+value);
                    }
                });
            }
        }
    }
}