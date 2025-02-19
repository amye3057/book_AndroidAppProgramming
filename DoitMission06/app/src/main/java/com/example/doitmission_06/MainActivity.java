package com.example.doitmission_06;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(50);

        TextView textView = findViewById(R.id.textView);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(""+seekBar.getProgress());
                progressBar.setProgress(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textView.setText(""+seekBar.getProgress());
                progressBar.setProgress(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText(""+seekBar.getProgress());
                progressBar.setProgress(seekBar.getProgress());
            }
        });
    }
}