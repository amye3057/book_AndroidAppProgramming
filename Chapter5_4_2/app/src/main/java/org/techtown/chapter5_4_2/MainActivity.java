package org.techtown.chapter5_4_2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.tab1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                    Toast.makeText(getApplicationContext(), "첫번째 탭 선택 됨", Toast.LENGTH_LONG).show();
                    return true;
                } else if (itemId == R.id.tab2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                    Toast.makeText(getApplicationContext(), "두번째 탭 선택 됨", Toast.LENGTH_LONG).show();
                    return true;
                } else if (itemId == R.id.tab3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                    Toast.makeText(getApplicationContext(), "세번째 탭 선택 됨", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

    }
}