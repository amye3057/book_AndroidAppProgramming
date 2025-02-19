package com.example.chapter5_6;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter5_6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 뷰바인딩을 통해 ActivityMainBinding 클래스로 레이아웃 뷰와 결합함
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // setSupportActionBar 메서드로 액션바 설정함
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        // drawer 레이아웃과 NavigationView 참조
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // 바로가기 메뉴를 사용하기 위해 NavigationUI를 사용함
        // -> 이를 위해 AppBarConfiguration 객체 생성
        // Builder를 이용해 사용할 navigation_graph와 DrawerLayout을 전달함
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        // NavHost의 Fragment들을 조종할 수 있는 NavigationController 객체 생성
        // NavHost를 참조하여 가져와 navController에 할당함
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        /* NavigationUI 클래스의 setupActionBarWithNavController 메서드를 사용하여
        Fragment의 전환에 따라 액션바도 Fragment를 따라 함께 변화하도록 세팅함 */
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // NavigaionUI 클래스의 setUpWithNavController 메서드를 사용해 NavigatioinView를 NavController에 맞게 구현함
        NavigationUI.setupWithNavController(navigationView, navController);


        // 추가한 부분 <
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        setSupportActionBar(binding.appBarMain.toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_content_main, fragment1).commit();
        getSupportActionBar().setTitle("첫 번째");

        // >

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            String message;

            // 툴바 타이틀 표시
            getSupportActionBar().setTitle(item.getTitle());

            // 각 메뉴 ID에 따른 메시지 설정
            if (id == R.id.nav_home) {
                message = item.getTitle().toString();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment1).commit();
            } else if (id == R.id.nav_gallery) {
                message = item.getTitle().toString();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment2).commit();
            } else if (id == R.id.nav_slideshow) {
                message = item.getTitle().toString();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment3).commit();
            } else {
                message = "기타";
            }
            // 토스트 메시지 표시
            Toast.makeText(this, message+" 메뉴가 선택되었습니다.", Toast.LENGTH_SHORT).show();

            // 메뉴 선택 처리 후 Drawer 닫기
            drawer.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // <뒤로가기> 버튼을 눌렀을 때의 처리
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}