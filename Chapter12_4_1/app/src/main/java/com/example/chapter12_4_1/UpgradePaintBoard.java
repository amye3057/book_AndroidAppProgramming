package com.example.chapter12_4_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UpgradePaintBoard extends View {

    Canvas canvas;
    Bitmap img;
    Paint paint;

    // 추가
    public boolean changed = false;
    float lastX;
    float lastY;

    Path path = new Path();
    float curveEndX;
    float curveEndY;

    int invalidateExtraBorder = 10;
    static final float TOUCH_TOLERANCE = 8;

    public UpgradePaintBoard(Context context) {
        super(context);
        init(context);
    }

    public UpgradePaintBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        // 추가
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3.0F);

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

        float X = (int) event.getX();
        float Y = (int) event.getY();

        switch(action){
            case MotionEvent.ACTION_UP:
                changed = true;
                Rect rect = processMove(event); // processMove 메서드 작성
                if(rect != null)
                    invalidate(rect);
                path.rewind();
                return true;

            case MotionEvent.ACTION_DOWN:
                lastX = X;
                lastY = Y;
                Rect invalidRect = new Rect();
                path.moveTo(X,Y);
                final int border = invalidateExtraBorder;
                invalidRect.set((int)X-border, (int)Y-border, (int)X+border, (int)Y+border);
                curveEndX = X;
                curveEndY = Y;
                canvas.drawPath(path, paint);
                if(invalidRect != null)
                    invalidate(invalidRect);
                return true;

            case MotionEvent.ACTION_MOVE:
                rect = processMove(event);
                if(rect != null)
                    invalidate(rect);
                return true;
        }

        return false;
    }

    private Rect processMove(MotionEvent event) {
        final float X = event.getX();
        final float Y = event.getY();
        final float dx = Math.abs(X-lastX);
        final float dy = Math.abs(Y-lastY);

        Rect invalidRect = new Rect();

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            final int border = invalidateExtraBorder;
            invalidRect.set((int)curveEndX-border, (int)curveEndY-border,
                           (int)curveEndX+border, (int)curveEndY+border);

            float cX = curveEndX = (X+lastX)/2;
            float cY = curveEndY = (Y+lastY)/2;

            path.quadTo(lastX, lastY, cX, cY);

            invalidRect.union((int)lastX-border, (int)lastY-border,
                             (int)lastX+border, (int)lastY+border);

            invalidRect.union((int)cX-border, (int)cY-border,
                             (int)cX+border, (int)cY+border);

            lastX = X;
            lastY = Y;
            canvas.drawPath(path, paint);
        }
        return invalidRect;
    }
}
