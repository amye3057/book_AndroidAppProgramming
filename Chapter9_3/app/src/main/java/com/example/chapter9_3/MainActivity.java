package com.example.chapter9_3;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    ArrayList<Drawable> drawablelist = new ArrayList<Drawable>();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        drawablelist.add(res.getDrawable(R.drawable.smile));
        drawablelist.add(res.getDrawable(R.drawable.fear));
        drawablelist.add(res.getDrawable(R.drawable.peaceful));
        drawablelist.add(res.getDrawable(R.drawable.angry));
        drawablelist.add(res.getDrawable(R.drawable.heart));

        imageView = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimThread thread = new AnimThread();
                thread.start();
            }
        });
    }

    class AnimThread extends Thread{
        public void run(){
            int index = 0;
            for(int i=0;i<100;i++){
                final Drawable drawable = drawablelist.get(index);
                index++;
                if(index>4) // 총 이미지는 5개니까 index는 0,1,2,3,4까지 가능
                    index=0;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });

                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}