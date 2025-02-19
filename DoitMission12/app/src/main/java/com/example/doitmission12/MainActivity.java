package com.example.doitmission12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 브로드캐스트 수신자 등록
        IntentFilter filter = new IntentFilter("com.example.CUSTOM_BROADCAST");
        registerReceiver(myReceiver, filter);

        inputText = findViewById(R.id.editTextText);
        outputText = findViewById(R.id.textView2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(getApplicationContext(), MyService.class);
                serviceIntent.putExtra("key",inputText.getText().toString());
                startService(serviceIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 브로드캐스트 수신자 해제
        unregisterReceiver(myReceiver);
    }

    // 내부 클래스 BroadcastReceiver 정의
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if ("com.example.CUSTOM_BROADCAST".equals(intent.getAction())) {
                // 브로드캐스트 수신 시 처리
                String text = intent.getStringExtra("key");
                outputText.setText(text+"(Broadcast)");
            }
        }
    };


    /*
    public static class MyReciever extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent broadcastIntent = new Intent(context, MainActivity.class);
            String text = intent.getStringExtra("key");
            // 확인용
            if(text!=null)
                Log.d("broadcast",text!=null?text:"null");
            broadcastIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            broadcastIntent.putExtra("key",text+"(Broadcast)");
            context.getApplicationContext().startActivity(broadcastIntent);
        }
    }*/
}