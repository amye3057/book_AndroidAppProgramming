package com.example.doitmission_15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_open = findViewById(R.id.button);
        button_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCustomerInputActivity();
            }
        });
    }

    public void goToCustomerInputActivity(){
        Intent intent = new Intent(getApplicationContext(), CustomerInput.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pageopen_page, R.anim.pageopen_main);
    }

}