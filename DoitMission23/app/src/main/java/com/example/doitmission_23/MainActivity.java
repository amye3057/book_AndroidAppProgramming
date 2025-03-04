package com.example.doitmission_23;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    boolean colorbool = true;
    int border = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 페인트보드를 세팅한다.
        PaintBoard paintBoard = new PaintBoard(this);
        LinearLayout main = findViewById(R.id.main);
        main.addView(paintBoard);
        paintBoard.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // 라디오 버튼 기본 세팅 (BUTT로 설정함)
        RadioButton BUTT = findViewById(R.id.radioButton);
        BUTT.setChecked(true);

        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton){ // BUTT
                    paintBoard.setStrokeCap(Paint.Cap.BUTT);
                    Toast.makeText(getApplicationContext(), "BUTT", Toast.LENGTH_LONG).show();
                }
                else if(checkedId == R.id.radioButton2){ // ROUND
                    paintBoard.setStrokeCap(Paint.Cap.ROUND);
                    Toast.makeText(getApplicationContext(), "ROUND", Toast.LENGTH_LONG).show();
                }
                else if(checkedId == R.id.radioButton3){ // SQUARE
                    paintBoard.setStrokeCap(Paint.Cap.SQUARE);
                    Toast.makeText(getApplicationContext(), "SQUARE", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button buttonColor = findViewById(R.id.button);
        buttonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorbool = !colorbool;
                paintBoard.setColor(colorbool);
            }
        });

        Button buttonWidth = findViewById(R.id.button2);
        buttonWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintBoard.setBorder(border);
                border++;
                if(border >= 4)
                    border = 0;
            }
        });
    }
}