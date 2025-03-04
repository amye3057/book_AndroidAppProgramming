package com.example.doitmission_23;

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

public class PaintBoard extends View {

    Canvas canvas;
    Bitmap bitmap;
    Paint paint;

    float lastX, lastY;
    Path path = new Path();
    float curveEndX, curveEndY;

    int invalidateExtraBorder = 10; // 이건 뭐니.
    static final float TOUCH_TOLERANCE = 8; // 대충 터치 감도.. 느낌으로 이해하면 될듯.
                                            // 작을 수록 더 섬세한 터치가 가능
    public boolean changed = false;

    public PaintBoard(Context context) {
        super(context);
        init(context);
    }

    public PaintBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        paint.setStyle(Paint.Style.STROKE); // 선

        paint.setStrokeCap(Paint.Cap.BUTT); // 기본값은 BUTT으로 설정
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(15.0F);

        this.lastX = -1;
        this.lastY = -1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap img = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas();
        canvas2.setBitmap((img));
        canvas2.drawColor(Color.WHITE);
        bitmap = img;
        canvas = canvas2;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if(bitmap != null){
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Rect rect;

        switch (action){
            case MotionEvent.ACTION_DOWN:
                rect = touchDown(event); // touchDown 작성
                if(rect!= null)
                    invalidate(rect); // 객체 무효화
                return true;

            case MotionEvent.ACTION_MOVE:
                rect = touch_Up_and_Move(event); // touch_Up_and_Move 작성
                if(rect!= null)
                    invalidate(rect); // 객체 무효화
                return true;

            case MotionEvent.ACTION_UP:
                changed = true;
                rect = touch_Up_and_Move(event); // touch_Up_and_Move 작성
                if(rect != null)
                    invalidate(rect); // 객체 무효화
                path.rewind(); // rewind() : 라인, 커브는 지우고, 내부정보는 다시 사용한다.
                return true;
        }
        return false;
    }

    ///////////////////// Rect를 반환하는 메서드들 ////////////////////////////

    private Rect touchDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        lastX = x;
        lastY = y;

        Rect rect = new Rect();
        path.moveTo(x,y);

        final int border = invalidateExtraBorder;
        rect.set((int)x-border, (int)y-border, (int)x+border, (int)y+border);

        curveEndX = x;
        curveEndY = y;

        canvas.drawPath(path, paint);
        return rect;
    }

    // action이 up이거나 move일 때 이 메서드가 실행됨.
    private Rect touch_Up_and_Move(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        final float dx = Math.abs(x-lastX);
        final float dy = Math.abs(y-lastY);
        // abs() : absolute value, 절댓값을 반환함.

        Rect rect = new Rect();

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE ){ // 터치 범위보다 크게 이동할 시 그림.
            final int border = invalidateExtraBorder;
            rect.set((int)curveEndX-border, (int)curveEndY-border, (int)curveEndX+border, (int)curveEndY+border);
            // set(left, right, top, bottom) 중심좌표를 기준으로 Rect 객체가 그려짐..

            float cx = curveEndX = (x+lastX)/2;
            float cy = curveEndY = (y+lastY)/2;

            path.quadTo(lastX,lastY,cx,cy); // lastX, lastY가 제어점임. cx,cy는 끝점임.
            // quadTo() : 제어점을 설정하여 곡선의 방향을 결정함. (last를 거쳐서 c로 이어지는 선)
            // quadTo는 2차 베지어 곡선, cubicTo는 3차 베지어 곡선(좀 더 정교함)

            rect.union((int)lastX - border, (int)lastY - border, (int)lastX+border, (int)lastY+border);
            rect.union((int)cx - border, (int)cy- border, (int)cx+border, (int)cy+border);
            /* union() : rect1와 파라미터로 들어간 rect2(Rect 객체로 넣거나 위처럼 left,top,right,bottom으로 넣어도 됨)를
                         포함하는 최소한의 사각형을 만들어서 rect1의 객체로 넘겨줌. strcat 같은 느낌임 근데 이제 Rect를 곁들인.*/
            lastX = x;
            lastY = y;
            canvas.drawPath(path,paint);
        }
        return rect;
    }

    ////////////////// MainActivity에서 사용할 메서드들 ///////////////////////

    int color[] = {Color.BLACK, Color.RED};
    float border[] = {5f, 10f, 15f, 30f};

    // 색상 설정 (검정, 빨강만 됨)
    public void setColor(boolean bool){
        // paint.setColor(color);
        if(bool){
            paint.setColor(color[0]);
        }
        else{
            paint.setColor(color[1]);
        }
    }

    // 굵기 설정
    public void setBorder(int i){
        paint.setStrokeWidth(border[i]);
    }

    //
    public void setStrokeCap(Paint.Cap strokeCap){
        paint.setStrokeCap(strokeCap);
    }
}
