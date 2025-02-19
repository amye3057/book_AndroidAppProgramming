package com.example.chapter6_3;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        checkPermissions(permissions);
    }

    public void checkPermissions(String[] permissions){
        ArrayList<String> targetList = new ArrayList<String>();

        int len = permissions.length;
        for(int i=0;i<len;i++){
            String curPermission = permissions[i];
            int permissionCheck = ContextCompat.checkSelfPermission(this, curPermission);

            if(permissionCheck== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, curPermission+" 권한 있음.", Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(this,curPermission+"권한 없음.",Toast.LENGTH_LONG).show();
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, curPermission))
                    Toast.makeText(this, curPermission+" 권한 설명 필요함", Toast.LENGTH_LONG).show();
                else
                    targetList.add(curPermission);
            }
        }
        String[] targets = new String[targetList.size()];

        targetList.toArray(targets);
        if(targets.length>0)
            ActivityCompat.requestPermissions(this, targets, 101); // 위험권한 부여 요청하기
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){ // 요청 코드가 맞는지 확인한다.
            case 101:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "첫번째 권한을 사용자가 승인한.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "첫번째 권한 거부됨.", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }
}