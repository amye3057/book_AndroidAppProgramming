package com.example.chaoter6_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSActicity extends AppCompatActivity {

    EditText editText_num, editText_content, editText_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsacticity);

        editText_num = findViewById(R.id.editTextText);
        editText_content = findViewById(R.id.editTextText2);
        editText_time = findViewById(R.id.editTextText3);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        if(intent!=null){ // 인텐트 안에 들어 있는 부가 데이터를 꺼내서 입력상자에 설정한다.
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String receivedDate = intent.getStringExtra("receivedDate");

            editText_num.setText(sender);
            editText_content.setText(contents);
            editText_time.setText(receivedDate);
        }
    }
}