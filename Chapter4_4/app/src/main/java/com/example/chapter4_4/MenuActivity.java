package com.example.chapter4_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    TextView textView;
    public static final String KEY_SIMPLE_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", "amy");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            SimpleData data = bundle.getParcelable(KEY_SIMPLE_DATA);
            if(intent!=null){
                textView.setText("전달받은 데이터\nNumber :"+data.number+"\nMessage : "+data.message);
            }
        }
    }
}