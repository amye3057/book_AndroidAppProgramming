package com.example.chapter6_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextText);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("command","show");
                intent.putExtra("name", name);

                startService(intent); // myService 클래스의 onStartCommand 객체로 전달
            }
        });
        // 액티비티가 '새로' 만들어질 때 전달된 인텐트 처리하기
        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        // 액티비티가 '이미' 만들어져 있을 때 전달된 인텐트 처리하기
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        if(intent != null){
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");

            Toast.makeText(this, "command : "+command+", name : "+name, Toast.LENGTH_LONG).show();
        }
    }
}