package com.example.chapter7_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class MyButton extends AppCompatButton {
    // 첫번째 생성자 : 이 뷰를 소스 코드에서 new 연산자로 생성하는 경우에 사용됨.
    public MyButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    // 두번째 생성자 : 이 뷰를 XML 레이아웃에 추가하는 경우에 사용됨.
    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        // AttributeSet 객체 : XML 레이아웃에서 태그에 추가하는 속성을 전달받기 위한 것
        super(context, attrs);
        init(context);
    }

    private void init(Context context) { // 뷰의 배경색과 글자색 설정
        setBackgroundColor(Color.CYAN);
        setTextColor(Color.BLACK);

        float textSize = getResources().getDimension(R.dimen.text_size);
        setTextSize(textSize);
        /* setTextSize에 바로 값을 넣을 경우 픽셀 단위 설정만 가능함.
        그래서 dimens.xml에 sp 단위의 크기 값을 정의한 다음, getDimension으로 가져오면 픽셀값으로 자동 변환된
        값을 넣을 수 있다. (sp 말고도 dp 등 다른 단위의 크기 값을 정의할 수 있다.)
         */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("MyButton","onDraw() 호출됨");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("MyButton","onTouchEvent() 호출됨");

        int action = event.getAction();

        switch(action){
            case MotionEvent.ACTION_DOWN: // 버튼이 눌린 경우
                setBackgroundColor(Color.BLUE);
                setTextColor(Color.RED);
                break;

            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.CYAN);
                setTextColor(Color.BLACK);
                break;
        }

        invalidate();
        return super.onTouchEvent(event);
    }
}
