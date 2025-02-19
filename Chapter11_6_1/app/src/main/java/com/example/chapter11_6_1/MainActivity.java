package com.example.chapter11_6_1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                // 연락처 화면을 띄우기 위한 인텐트 만들기
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    // 사용자가 연락처를 하나 선택할 시 자동으로 호출된다.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == 101){
                try{
                    Uri uri = data.getData();
                    String id = uri.getLastPathSegment(); // 선택한 연락처의 id 값 확인하기
                    getContacts(id); // getContacts 메서드 작성하기

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void getContacts(String id) {
        Cursor cursor = null;
        String name = "";

        try {
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, // 연락처의 상세 정보를 조회하는 용도의 URI
                    null,
                    ContactsContract.Data.CONTACT_ID + "=?", // ContactsContract.Data.CONTACT_ID : id 칼럼의 이름
                    new String[] { id },
                    null);

            if(cursor.moveToFirst()){
                name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME)); // 이름 칼럼
                textView.append("Name : " + name + "\n");

                String columns[] = cursor.getColumnNames();
                for(String column : columns){ // 모든 칼럼의 이름과 그 칼럼의 값을 출력한다.
                    int index = cursor.getColumnIndex(column);
                    Log.d("log",index + column);
                    textView.append("#" + index + "-> [" + column + "] " + cursor.getString(index) + "\n");
                }
                cursor.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없으면 요청
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
        }
    }

}