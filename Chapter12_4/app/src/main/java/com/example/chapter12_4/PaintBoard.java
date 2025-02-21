package com.example.chapter12_4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PaintBoard extends View {
    Canvas canvas;
    Bitmap img;
    Paint paint;

    int lastX;
    int lastY;

    public PaintBoard(Context context) {
        super(context);
        init(context);
    }

    public PaintBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);

        this.lastX = -1;
        this.lastY = -1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        img = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if(img!=null){
            canvas.drawBitmap(img,0,0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch(action){
            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;
                break;
            case MotionEvent.ACTION_DOWN:
                if(lastX != -1 && (X != lastX || Y != lastY)){
                    canvas.drawLine(lastX, lastY, X, Y, paint);
                }
                lastX = X;
                lastY = Y;
                break;
            case MotionEvent.ACTION_MOVE:
                if(lastX != -1){
                    canvas.drawLine(lastX, lastY, X, Y, paint);
                }
                lastX=X;
                lastY=Y;
                break;
            // ACTION_DOWN(눌림) 상태에서 좌표 값을 변수에 저장한 후
            // ACTION_MOVE(이동) 상태에서 이전의 좌표 값(lastX,lastY)과 현재의 좌표 값(X,Y)을 연결하여 선을 그림.
        }

        invalidate(); // invalidate() : View의 onDraw() 메서드를 다시 호출하여 View의 내용을 다시 그리도록 요청함.
        // -> 이동(ACTION_MOVE) 중에도 지속적으로 화면 갱신.
        return true;
    }
}
