package com.example.doitmission24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Pannel extends View {
    int offsetX, offsetY; // rect 내부 어딜 눌렀는지 기억
    int size = 200; // 200x200 사이즈 정사각형
    Rect rect;
    boolean isDragging = false;

    public Pannel(Context context) {
        super(context);
        rect = new Rect(100, 100, 100+size, 100+size);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(rect,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(rect.contains(touchX,touchY)){ // contains() : 주어진 좌표나 사각형이 rect 안에 있는지 판단해줌. (경계선은 포함 x)
                    // 사각형을 터치했다는 의미
                    isDragging = true;
                    offsetX = touchX - rect.left;
                    offsetY = touchY - rect.top;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDragging){
                    int left = touchX - offsetX;
                    int top = touchY - offsetY;
                    rect.set(left, top, left + rect.width(), top + rect.height());
                    invalidate(); // 다시 그리기. onDraw()가 다시 호출됨
                }
                break;
            case MotionEvent.ACTION_UP:
                isDragging = false;
                break;
        }
        return true;
    }
}
