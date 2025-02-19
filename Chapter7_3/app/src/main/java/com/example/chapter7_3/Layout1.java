package com.example.chapter7_3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Layout1 extends LinearLayout {
    ImageView imageView;
    TextView textView1;
    TextView textView2;

    public Layout1(Context context) {
        super(context);
        init(context);
    }

    public Layout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        // 인플레이션 진행하기
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout1, this, true);

        // 인플레이션 과정이 끝나면 XML 레이아웃 안에 있는 뷰를 참조할 수 있다.
        imageView = findViewById(R.id.imageView);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }

    public void setName (String name){
        textView1.setText(name);
    }

    public void setMoblie (String mobile){
        textView2.setText(mobile);
    }
}
