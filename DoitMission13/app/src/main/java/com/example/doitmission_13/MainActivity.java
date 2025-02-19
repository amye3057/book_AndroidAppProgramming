package com.example.doitmission_13;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    int n=0; // 추가한 아이템 수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView_person);
        EditText editText_name = findViewById(R.id.editText_name);
        EditText editText_birth = findViewById(R.id.editText_birth);
        EditText editText_mobile = findViewById(R.id.editText_mobile);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        PersonAdapter adapter = new PersonAdapter();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_name.getText().toString();
                String birth = editText_birth.getText().toString();
                String mobile = editText_mobile.getText().toString();

                adapter.addItem(new Person(name, birth, mobile));
                recyclerView.setAdapter(adapter);

                n++;
                textView.setText(n+"명");
            }
        });

    }
}