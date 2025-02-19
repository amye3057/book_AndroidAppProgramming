package com.example.chapter8_2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    boolean isPageOpen = false;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    LinearLayout page;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = findViewById(R.id.page);

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPageOpen){ // 페이지가 열려 있으면
                    page.startAnimation(translateRightAnim); // 닫는다
                }
                else{ // 페이지가 닫혀 있으면
                    page.setVisibility(View.VISIBLE); // 보이게 하고
                    page.startAnimation(translateLeftAnim); // 연다
                }
            }
        });
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener{
        // 필수 메서드 3개 onAnimation Start, End, Repeat
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isPageOpen){ // 페이지가 열려있다면
                page.setVisibility(View.INVISIBLE); // 얘를 리스너에 묶거나 온클릭에 묶어서 넣으면 되지 않나
                button.setText("Open");             // 왜 굳이 하나는 리스너에 하나는 온클릭에..
                isPageOpen=false;
            }
            else{
                button.setText("Close");
                isPageOpen=true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}