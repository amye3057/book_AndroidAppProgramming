package com.example.chapter6_3;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermission.with(this).runtime().permission(
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE).onGranted(new Action<List<String>>(){
            @Override
            public void onAction(List<String> permissions) {
                String message = "허용된 권한 개수"+permissions.size();
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                String message = "거부된 권한 개수"+permissions.size();
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        }).start();
    }
}