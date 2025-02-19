package com.example.chapter3_5;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            showToast("방향 : ORIENTATION_LANDSCAPE");
        }
        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            showToast("방향 : ORIENTATION_PORTRAIT");
        }
    }

    public void showToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}