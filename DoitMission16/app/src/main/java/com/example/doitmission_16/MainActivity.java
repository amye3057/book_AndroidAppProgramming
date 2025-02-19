package com.example.doitmission_16;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button btn_input = findViewById(R.id.btn_input);
        WebView webView = findViewById(R.id.webView);
        View tab = findViewById(R.id.tab);
        EditText editText = findViewById(R.id.editTextText);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new ViewClient());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim;

                if(tab.getVisibility()== VISIBLE){
                    anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up);
                    tab.setVisibility(View.INVISIBLE);
                    button.setText("열기");
                }
                else{
                    tab.setVisibility(VISIBLE);
                    anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down);
                    button.setText("닫기");
                }
                tab.startAnimation(anim);
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(editText.getText().toString());
            }
        });
    }

    private class ViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            view.loadUrl(url);
            return true;
        }
    }
}