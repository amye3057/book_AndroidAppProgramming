package com.example.chapter10_3;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String urlString = editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlString); // request 메서드 작성
                    }
                }).start();
            }
        });
    }

    private void request(String urlString) {
        StringBuilder output = new StringBuilder();

        try{
            URL url = new URL(urlString);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            // URL 객체를 만들고 openConnection 메서드를 호출하여 HttpURLConnection 객체를 만들기

            if(urlconnection!=null){
                urlconnection.setConnectTimeout(10000); // 연결대기 시간 10초로 설정
                urlconnection.setRequestMethod("GET"); // GET 방식으로 요청
                urlconnection.setDoInput(true); // 해당 객체의 입력이 가능하도록 설정

                int resCode = urlconnection.getResponseCode();
                // 정상 응답코드 : HTTP_OK
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
                // readLine 메서드를 사용하기 위해 urlconnection 스트림을 BufferedReader 객체로 만든다.

                String line = null;
                while(true){
                    line = reader.readLine(); // readLine : 스트림에서 한 줄씩 읽어들이는 메서드
                    if(line==null)
                        break;

                    output.append(line+"\n");
                }

                reader.close();
                urlconnection.disconnect();
            }

        }catch (Exception ex){
            println("예외 발생함 : "+ex.toString());
        }

        println("응답 -> "+output);

    }

    // 출력 메서드
    private void println(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }
}