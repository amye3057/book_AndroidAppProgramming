package com.example.chapter9_1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    MainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new MainHandler(); // 핸들러 객체 만들기

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackGroundThread thread = new BackGroundThread();
                thread.start(); // 스레드 객체 생성 후 실행
            }
        });
    }

    // 스레드를 실행하면 그 안에 들어있는 run 메서드가 실행된다.
    class BackGroundThread extends Thread{
        int value = 0;

        public void run(){
            for(int i=0;i<100;i++) {
                try {
                    Thread.sleep(1000); // 지정된 시간(1초) 동안 스레드 일시중지
                } catch (Exception e) {
                }
                // try-catch 문 : 예외상황(Exception)이 발생할 시 실행.

                value += 1; // 1초마다 value 값이 1씩 상승
                Log.d("Thread", "value : " + value); // 1초마다 value 값 출력

                Message message = handler.obtainMessage();
                // Message 객체에는 Bundle 객체가 들어있어서 put@@@ 메서드와 get@@@ 메서드로 데이터 이동이 가능하다.
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);

                handler.sendMessage(message); // 메시지 큐에 넣은 message는 순서대로 핸들러가 처리하게 된다.
            }
        }
    }

    // 핸들러의 handlerMessage 메서드에 정의된 기능이 수행돤다.
    class MainHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("value 값 : "+value);
        }
    }
}