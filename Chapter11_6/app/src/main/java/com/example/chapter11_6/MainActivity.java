package com.example.chapter11_6;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위험권한 부여
        AndPermission.with(this).runtime().permission(
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE).onGranted(new Action<List<String>>(){
            @Override
            public void onAction(List<String> permissions) {
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
            }
        }).start();

        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*"); // MIME 타입이 image로 시작하는 데이터를 가져오도록 설정한다.
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101); // 앨범에서 사진을 선택할 수 있는 화면이 띄워짐.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // data라는 변수가 참조하는 인텐트 객체를 사용할 수 있음.

        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();

                try{
                    InputStream inputStream = resolver.openInputStream(fileUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(bitmap);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}