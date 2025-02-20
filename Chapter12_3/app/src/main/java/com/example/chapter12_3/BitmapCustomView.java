package com.example.chapter12_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BitmapCustomView extends View {

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    // 메모리에 만들어질 Bitmap 객체와 Bitmap 객체에 그리기 위한 Canvas 객체 선언
    private Paint paint;

    public BitmapCustomView(Context context) {
        super(context);
        init(context);
    }

    public BitmapCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 뷰가 화면에 보이기 전에 Bitmap 객체 만들고 그 위에 그리기

        // CacheBitmap 생성하기

        cacheBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);

        // 빨간 사각형 그리기
        cacheCanvas.drawColor(Color.WHITE);
        paint.setColor(Color.RED);
        cacheCanvas.drawRect(100,100,200,200,paint);

        /* 리소스에 저장된 이미지 읽어들여서 화면에 그리기 */

        // 그냥 그리기
        Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        cacheCanvas.drawBitmap(srcImg,30,30, paint);

        // 좌우로 뒤집어서 그리기
        Matrix horInvMatrix = new Matrix(); // horizontical Inverse Matrix (좌우 대칭)
        horInvMatrix.setScale(-1,1);
        Bitmap horInvImg = Bitmap.createBitmap(
                srcImg,0,0,srcImg.getWidth(), srcImg.getHeight(), horInvMatrix, false);
        cacheCanvas.drawBitmap(horInvImg,30,330, paint);

        // 상하로 뒤집어서 그리기
        Matrix verInvMatrix = new Matrix(); // Vertical Inverse Matrix (상하 대칭)
        verInvMatrix.setScale(1,-1);
        Bitmap verInvImg = Bitmap.createBitmap(
                srcImg,0,0,srcImg.getWidth(),srcImg.getHeight(),verInvMatrix,false);
        cacheCanvas.drawBitmap(verInvImg,30,630, paint);

        // 마스크 필터 사용하기(번짐 효과) + 크기 키우기
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
        Bitmap scaledImg = Bitmap.createScaledBitmap(srcImg, srcImg.getWidth()*3,srcImg.getHeight()*3, false);
        cacheCanvas.drawBitmap(scaledImg,30,700, paint);


    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if(cacheBitmap!=null){
            canvas.drawBitmap(cacheBitmap,0,0,null);
        }
    }

}
