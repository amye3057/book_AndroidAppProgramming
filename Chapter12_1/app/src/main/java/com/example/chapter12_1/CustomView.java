package com.example.chapter12_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomView extends View {

    private Paint paint; // Paint 객체 : 그래픽을 그리기 위해 필요한 속성을 담고 있음.

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    // onDraw : 뷰가 화면에 그려질 때 자동으로 호출됨
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(100,100,200,200, paint);
        // drawRect : 좌표값과 Paint 객체를 이용해서 사각형을 그리는 메서드
    }

    @Override
    // onTouchEvent : 터치 이벤트를 처리하는 일반적인 방법을 제공.
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){ // 손가락으로 누른 경우
            Toast.makeText(super.getContext(),
                    "MotionEvent.Action_DOWN : " + event.getX() + "," + event.getY(), Toast.LENGTH_LONG).show();
        } // 손가락으로 누른 곳의 X,Y 좌표를 토스트 메시지로 띄움.

        return super.onTouchEvent(event);
    }
}
