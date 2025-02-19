package org.techtown.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.fragment.R;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contianor);
        mainFragment = new MainFragment();
        menuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianor, mainFragment).commit();
    }

    public void onFragmentChanged(int index){
        if(index==0)
            getSupportFragmentManager().beginTransaction().replace(R.id.contianor, menuFragment).commit();
        else if(index==1){
            menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.contianor);
            getSupportFragmentManager().beginTransaction().replace(R.id.contianor, mainFragment).commit();
        }
    }
}