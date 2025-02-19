package com.example.chapter4_6;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"onCreate 호출됨", Toast.LENGTH_LONG).show();
        System.out.println("onCreate 호출됨");

        nameInput = findViewById(R.id.nameInput);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"onStart 호출됨", Toast.LENGTH_LONG).show();
        System.out.println("onStart 호출됨");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"onStop 호출됨", Toast.LENGTH_LONG).show();
        System.out.println("onStop 호출됨");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"onResume 호출됨", Toast.LENGTH_LONG).show();
        System.out.println("onResume 호출됨");
        restoreState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"onPause 호출됨", Toast.LENGTH_LONG).show();
        System.out.println("onPause 호출됨");
        saveState();
    }

    protected void restoreState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref!=null)&&(pref.contains("name"))){
            String name = pref.getString("name","");
            nameInput.setText(name);
        }
    }

    protected void saveState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name",nameInput.getText().toString());
        editor.commit();
    }

    protected void clearState(){
        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"onDestroy 호출됨", Toast.LENGTH_LONG).show();
        System.out.println("onDestroy 호출됨");
    }
}