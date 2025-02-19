package com.example.doitmission_07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    EditText editText1, editText2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_CANCELED){
                String activity = data.getExtras().getString("activity");
                System.out.println(activity);
                // 문제 발생.
                Toast.makeText(getApplicationContext(), activity+" 화면으로부터 응답",Toast.LENGTH_LONG).show();
            }
            if(resultCode!= RESULT_OK){ // 성공 값이 아니라면 return
                return;
            }
            /*
            String text = data.getExtras().getString("text");
            System.out.println(text); // 확인용
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editTextText);
        editText2 = findViewById(R.id.editTextText2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println(editText1.getText());
                if(editText1.getText().length()==0 || editText2.getText().length()==0) { // 길이가 0, 즉 입력이 없을 경우 토스트 띄움
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                    //finish();
                }
            }
        });
    }
}