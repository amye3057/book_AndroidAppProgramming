package com.example.doitmission_17;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout pannel1, pannel2;
    TextView textViewnum;
    Animation show, disappear;
    Handler handler = new Handler();
    int x=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pannel1 = findViewById(R.id.pannel1);
        pannel2 = findViewById(R.id.pannel2);
        disappear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right2left_move);
        show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right2left_stay);
        textViewnum=findViewById(R.id.textView2);
        textViewnum.setText("1/2"); // 현재 아이템/전체 아이템의 개수

        AnimThread thread = new AnimThread();
        thread.start();
    }

    class AnimThread extends Thread{
        @Override
        public void run() {

            while(true){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){}

                if(x==1){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pannel1.startAnimation(disappear);
                            pannel2.startAnimation(show);
                            textViewnum.setText(x+"/2");
                            x++;
                        }
                    });
                }
                else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pannel1.startAnimation(show);
                            pannel2.startAnimation(disappear);
                            textViewnum.setText(x+"/2");
                            x--;
                        }
                    });
                }

            }
        }
    }
}