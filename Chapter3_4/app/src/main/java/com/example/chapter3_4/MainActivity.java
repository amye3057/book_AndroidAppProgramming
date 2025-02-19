package com.example.chapter3_4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String name;
    EditText editText;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name",name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToast("OnCreate 호출됨.");

        //추가한 코드
        editText = findViewById(R.id.editTextText);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText.getText().toString();
                showToast("입력된 값을 변수에 입력했습니다 : "+name);
            }
        });

        if(savedInstanceState != null){
            name = savedInstanceState.getString("name");
            showToast("값을 복원했습니다 : "+name);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("onStart 호출됨.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("onStop 호출됨.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("onDestroy 호출됨.");
    }

    public void showToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}