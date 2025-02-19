package com.example.chapter7_5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String[] items = { "Amy", "Sheldon", "Penny", "Howard", "Leonard", "Raj"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Spinner spinner = findViewById(R.id.spinner);

        // API에서 제공하는 기본 어댑터를 사용함.
        // ArrayAdapter : 배열로 된 데이터를 아이템으로 추가할 때 사용함.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        // <<두번째 파라미터>>는 뷰를 초기화할 때 사용되는 XML 레이아웃의 리소스 ID 값이다.
        /* simple_spinner_item : 스피너를 간단하게 사용할 수 있도록 API에서 제공하는 레이아웃.
                                 문자열을 아이템으로 보여주는 단순 스피너 아이템의 레이아웃이다.*/
        //<<세번째 파라미터는>> 아이템으로 보일 문자열 데이터들의 배열이다.

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // setDropDownViewResource : 스피너의 각 아이템들을 보여줄 뷰에 사용되는 레이아웃을 지정하는데 사용되는 메서드.
        // android.R.layout.simple_spinner_dropdown_item : 이걸로 설정하면 가장 단순한 형태의 뷰로 보임.

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(items[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
            }
        });
    }
}