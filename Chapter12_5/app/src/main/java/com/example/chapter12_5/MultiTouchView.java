package com.example.chapter12_5;

import static androidx.core.view.VelocityTrackerCompat.recycle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MultiTouchView extends View implements View.OnTouchListener{
    // 터치 이벤트를 처리하므로 OnTouchListener 인터페이스를 구현하도록 한다. (인터페이스에서 onTouch 메서드를 포함함.)
    // 터치 이벤트가 발생할 때마다 onTouch 메서드가 호출됨.

    private static final String TAG = "MultiTouchView";
    Context mContext;
    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;

    int lastX, lastY;
    Bitmap sourceBitmap;
    Matrix mMatrix;

    float sourceWidth = 0.0F, sourceHeight = 0.0F;
    float bitmapCenterX, bitmapCenterY;
    float scaleRatio;
    float totalScaleRatio;
    float displayWidth = 0.0F, displayHeight = 0.0F;
    int displayCenterX = 0, displayCenterY = 0;
    public float startX, startY;
    public static float MAX_SCALE_RATIO = 5.0F, MIN_SCALE_RATIO = 0.1F;
    float oldDistance = 0.0F;
    int oldPointerCount = 0;
    boolean isScrolling = false;
    float distanceThreshold = 3.0F;


    public MultiTouchView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint();
        mMatrix = new Matrix();

        lastX = -1;
        lastY = -1;

        setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w>0 && h>0){
            newImage(w,h);
            redraw();
        }
    }
    public void newImage(int width, int height) {
        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);

        mBitmap = img;
        mCanvas = canvas;

        displayWidth = (float)width;
        displayHeight = (float)height;

        displayCenterX = width/2;
        displayCenterY = height/2;
    }
    public void redraw() {
        if (sourceBitmap == null) {
            Log.d(TAG, "sourceBitmap is null in redraw().");
            return;
        }

        drawBackground(mCanvas);

        float originX = (displayWidth - (float)sourceBitmap.getWidth()) / 2.0F;
        float originY = (displayHeight - (float)sourceBitmap.getHeight()) / 2.0F;

        mCanvas.translate(originX, originY);
        mCanvas.drawBitmap(sourceBitmap, mMatrix, mPaint);
        mCanvas.translate(-originX, -originY);

        invalidate();
    }
    public void drawBackground(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if(mBitmap != null){
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction();
        int pointerCount = event.getPointerCount();
        Log.d(TAG, "Pointer Count : " + pointerCount);

        switch(action){
            // 손가락으로 눌렀을 때
            case MotionEvent.ACTION_DOWN:
                if(pointerCount == 1){ // 손가락 하나
                    startX = event.getX();
                    startY = event.getY();
                }
                else if(pointerCount == 2){ // 손가락 두개
                    oldDistance = 0.0F;
                    isScrolling = true;
                }

            // 손가락으로 누른 채 움직일 때
            case MotionEvent.ACTION_MOVE:
                if(pointerCount == 1){ // 손가락 하나
                    if(isScrolling)
                        return true;

                    float curX = event.getX();
                    float curY = event.getY();

                    if(startX == 0.0F){
                        startX = curX;
                        startY = curY;
                        return true;
                    }

                    float offsetX = startX-curX, offsetY = startY-curY;
                    if(oldPointerCount != 2 ){
                        Log.d(TAG, "ACTION MOVE : " + offsetX + ", " + offsetY);
                        if(totalScaleRatio > 1.0F)
                            moveImage(-offsetX, -offsetY);
                        // 한 손으로 움직이고 있을 때는 moveImage 호출
                    }
                }
                else if(pointerCount == 2){ // 손가락 2개
                    float x1 = event.getX(0);
                    float y1 = event.getY(0);
                    float x2 = event.getX(1);
                    float y2 = event.getY(1);

                    float dx = x1 - x2;
                    float dy = y1 - y2;
                    float distance = new Double(Math.sqrt(new Float(dx * dx + dy * dy).doubleValue())).floatValue();

                    float outScaleRatio = 0.0F;
                    if (oldDistance == 0.0F) {
                        oldDistance = distance;
                        break;
                    }

                    if (distance > oldDistance) {
                        if ((distance-oldDistance) < distanceThreshold) {
                            return true;
                        }

                        outScaleRatio = scaleRatio + (oldDistance / distance * 0.05F);
                    } else if (distance < oldDistance) {
                        if ((oldDistance-distance) < distanceThreshold) {
                            return true;
                        }

                        outScaleRatio = scaleRatio - (distance / oldDistance * 0.05F);
                    }

                    if (outScaleRatio < MIN_SCALE_RATIO || outScaleRatio > MAX_SCALE_RATIO) {
                        Log.d(TAG, "Invalid scaleRatio : " + outScaleRatio);
                    } else {
                        Log.d(TAG, "Distance : " + distance + ", ScaleRatio : " + outScaleRatio);
                        scaleImage(outScaleRatio);
                    }

                    oldDistance = distance;
                }

                oldPointerCount = pointerCount;

                break;

            // 손가락을 떼었을 때
            case MotionEvent.ACTION_UP:
                if(pointerCount == 1){
                    float curX = event.getX(), curY = event.getY();
                    float offsetX = startX-curX, offsetY = startY-curY;

                    if(oldPointerCount != 2){
                        moveImage(-offsetX, -offsetY);
                    }
                }
                else
                    isScrolling = false;

                return true;
        }
        return true;
    }

    // 매트릭스 객체를 사용해 이미지 이동
    private void moveImage(float offsetX, float offsetY) {
        Log.d(TAG,"moveImage() 호출 : " + offsetX + ", " + offsetY);
        mMatrix.postTranslate(offsetX, offsetY);
        redraw();
    }

    // 매트릭스 객체를 사용해 이미지 크기 변경
    private void scaleImage(float inScaleRatio) { // 왜 넣을 땐 outScaleRatio인데 굳이 여기 파라미터 이름은 inScale로 바꾼거지
        Log.d(TAG,"scaleImage() 호출 : " + inScaleRatio);

        mMatrix.postScale(inScaleRatio, inScaleRatio, bitmapCenterX, bitmapCenterY);
        mMatrix.postRotate(0);
        totalScaleRatio = totalScaleRatio * inScaleRatio;
        redraw();
    }

    public void setImageData(Bitmap image) {
        recycle();

        sourceBitmap = image;

        sourceWidth = sourceBitmap.getWidth();
        sourceHeight = sourceBitmap.getHeight();

        bitmapCenterX = sourceBitmap.getWidth()/2;
        bitmapCenterY = sourceBitmap.getHeight()/2;

        scaleRatio = 1.0F;
        totalScaleRatio = 1.0F;
    }

    public void recycle() {
        if (sourceBitmap != null) {
            sourceBitmap.recycle();
        }
    }
}
