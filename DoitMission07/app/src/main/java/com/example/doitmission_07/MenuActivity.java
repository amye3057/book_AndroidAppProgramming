package com.example.doitmission_07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CM = 201;
    public static final int REQUEST_CODE_SM = 202;
    public static final int REQUEST_CODE_PM = 203;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            String activity2 = data.getExtras().getString("activity");
            Intent intent = new Intent();
            intent.putExtra("activity", activity2);
            setResult(RESULT_CANCELED, intent);
            finish(); // 로그인 화면으로(가장 초기 화면으로)
        }
        if(resultCode!=RESULT_OK)
            return;
        if(data!=null) {
            String activity = data.getStringExtra("activity");
            if (activity != null) {
                Toast.makeText(getApplicationContext(), activity + " 화면으로부터 응답.", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerManage.class);
                startActivityForResult(intent, REQUEST_CODE_CM);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SaleManage.class);
                startActivityForResult(intent, REQUEST_CODE_SM);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductManage.class);
                startActivityForResult(intent, REQUEST_CODE_PM);
            }
        });
    }
}