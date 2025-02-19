package com.example.doitmission_15;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_input);

        EditText editText = findViewById(R.id.editTextText);
        EditText editText1 = findViewById(R.id.editTextText2);
        EditText editText2 = findViewById(R.id.editTextText3);

        Button button_close = findViewById(R.id.button2);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String age = editText1.getText().toString();
                String birth = editText2.getText().toString();

                Toast.makeText(getApplicationContext(), "이름:"+name+", 나이:"+age+", 생년월일:"+birth, Toast.LENGTH_LONG).show();
                finish();
                overridePendingTransition(R.anim.mainopen_main, R.anim.mainopen_page);
            }
        });
    }
}