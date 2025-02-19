package com.example.chapter5_5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰페이저
        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter(this);

        Fragment1 fragment1 = new Fragment1();
        adapter.addItem(fragment1);

        Fragment2 fragment2 = new Fragment2();
        adapter.addItem(fragment2);

        Fragment3 fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);

        // 버튼
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0); // items[0] 으로 이동
            }
        });

        // 타이틀스트립 대체 (뷰페이저1에서만 되는거..라서 따로 코드 짬)
        TabLayout tabLayout = findViewById(R.id.tablayout);
        new TabLayoutMediator(tabLayout, pager, (tab, position) -> {
            tab.setText("페이지 " + position); // 각 탭 제목 설정
        }).attach();

    }

    class MyPagerAdapter extends FragmentStateAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();
        public MyPagerAdapter(AppCompatActivity activity){
            super(activity);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}