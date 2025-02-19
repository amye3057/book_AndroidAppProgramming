package com.example.chapter12_1_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StyleCustomView extends View {
    Paint paint;

    public StyleCustomView(Context context) {
        super(context);
        init(context);
    }

    public StyleCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // 첫번째 사각형 (빨간색 채우기, 초록색 테두리)
        paint.setStyle(Paint.Style.FILL); // 도구를 변경한다고 보면 이해가 쉽다. FILL(채우기)로 설정
        paint.setColor(Color.RED);
        canvas.drawRect(10,10,100,100, paint);
        paint.setStyle(Paint.Style.STROKE); // STROKE로 설정 (외곽선)
        paint.setStrokeWidth(2.0F);
        paint.setColor(Color.GREEN);
        canvas.drawRect(10,10,100,100, paint);

        // 두번째 사각형 (반투명한 파란색 채우기, 초록색 테두리)
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(128,0,0,255); // 투명도 128, 블루 255
        canvas.drawRect(120,10,210,100, paint);
        // DashPathEffect : 점선으로 그리고 싶을 때 사용
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{5,5}, 1);
        // {선이 그려지는 부분, 그려지지 않는 부분}
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0F);
        paint.setPathEffect(dashPathEffect);
        paint.setColor(Color.GREEN);
        canvas.drawRect(120,10,210,100, paint);

        paint = new Paint(); // 왜 굳이 한번 바꿔주는거지. 붓 한번 헹구는 느낌인가..?

        // 첫번째 원
        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(50,160,40, paint);

        // 두번째 원
        paint.setAntiAlias(true); // setAntiAlias(true) : 부드럽게 선을 그릴 때 사용
        canvas.drawCircle(160,160,40, paint);

        // 첫번째 텍스트
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(30);
        canvas.drawText("Text (Stroke)", 20, 260, paint);

        // 두번째 텍스트
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30);
        canvas.drawText("Text", 20,320, paint);
    }
}
