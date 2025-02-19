package com.example.chapter5_3;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ActionBar abar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        abar = getSupportActionBar();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abar.setLogo(R.drawable.bone);
                abar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_USE_LOGO);
            }
        });
    }

    // 액티비티가 만들어질 때 미리 자동 호출되어, 화면에 메뉴 기능 추가.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        View v = menu.findItem(R.id.menu_search).getActionView();
        if(v != null){
            EditText editText = v.findViewById(R.id.editTextText);

            if(editText!=null){
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
            }
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //int curId = item.getItemId();
        String curTitle = item.getTitle().toString();
        Toast.makeText(this, curTitle+" 메뉴가 선택되었습니다.", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

}