package com.example.chapter12_2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class DrawableCustomView extends View {

    private ShapeDrawable upperDrawable;
    private ShapeDrawable lowerDrawable;

    public DrawableCustomView(Context context) {
        super(context);
        init(context);
    }

    public DrawableCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        // WindowManager를 이용해 뷰의 폭과 높이 확인

        Resources curRes = getResources();
        // Color를 사용할 때는 Color 클래스에 정의된 상수 또는 argb메서드를 이용한다. (ex. Color.argb(128,0,0,255))
        int blackColor = curRes.getColor(R.color.color01);
        int grayColor = curRes.getColor(R.color.color02);
        int darkGrayColor = curRes.getColor(R.color.color03);
        // color01, color02, color03은 values > colors.xml에서 추가한다.


        upperDrawable = new ShapeDrawable();

        RectShape rectangle = new RectShape();
        rectangle.resize(width, height*2/3);
        upperDrawable.setShape(rectangle);
        upperDrawable.setBounds(0,0,width,height*2/3); // 시작지점을 0으로 정함. (윗부분)

        LinearGradient gradient = new LinearGradient(
                0,0,0,height*2/3, grayColor, blackColor, Shader.TileMode.CLAMP);
        // 높이 0~3분의2만큼

        Paint paint = upperDrawable.getPaint();
        paint.setShader(gradient); // 윗부분 3분의2 그라데이션 설정



        lowerDrawable = new ShapeDrawable();

        RectShape rectangle2 = new RectShape();
        rectangle2.resize(width, height*1/3);
        lowerDrawable.setShape(rectangle2);
        lowerDrawable.setBounds(0,height*2/3,width,height); // 시작지점을 높이의 3분의2로 정함. (아랫부분)

        LinearGradient gradient2 = new LinearGradient(
                0,0,0,height*1/3, blackColor, darkGrayColor, Shader.TileMode.CLAMP);
        // 높이 0(0은 시작지점, 시작지점은 3분의 2로 정해짐.)~3분의 1만큼

        Paint paint2 = lowerDrawable.getPaint();
        paint2.setShader(gradient2); // 아랫부분 3분의1 그라데이션 설정

    }

    protected void onDraw(Canvas canvas){
        // 그라데이션
        super.onDraw(canvas);
        upperDrawable.draw(canvas);
        lowerDrawable.draw(canvas);


        // 노란색 선 그리기
        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.YELLOW);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(16.0F);

        // setStrokeCap : 선의 끝 모양을 지정함.
        pathPaint.setStrokeCap(Paint.Cap.BUTT); // BUTT : 지정된 좌표에서 선이 끝남. (DEFAULT)
        // setStrokeJoin : 선이 만나 각지는 곳의 모양을 지정함.
        pathPaint.setStrokeJoin(Paint.Join.MITER); // MITER : 90도 각진 형태 (DEFAULT)

        Path path = new Path();
        path.moveTo(20,20);
        path.lineTo(120,20);
        path.lineTo(160,90);
        path.lineTo(180,80);
        path.lineTo(200,120);

        canvas.drawPath(path, pathPaint); // Paint 객체는 도구고, Path 객체는 화가다.


        // 하얀색 선 그리기
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStrokeCap(Paint.Cap.ROUND); // ROUND : 둥근 모양으로 끝남.
        pathPaint.setStrokeJoin(Paint.Join.ROUND); // ROUND : 둥근 모양

        path.offset(30,120); // offset으로 좌표를 이동한 뒤 그린다.
        canvas.drawPath(path, pathPaint);


        // 파란색 선 그리기
        pathPaint.setColor(Color.CYAN);
        pathPaint.setStrokeCap(Paint.Cap.SQUARE); // SQUARE : 사각형 모양으로 끝남.
        pathPaint.setStrokeJoin(Paint.Join.BEVEL); // BEVEL : 깎은 모양

        path.offset(30,120);
        canvas.drawPath(path, pathPaint);
    }
}
