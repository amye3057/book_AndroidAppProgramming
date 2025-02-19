package com.example.chapter8_3;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.EditText);
        webView = findViewById(R.id.WebView);

        WebSettings webSettings = webView.getSettings();
        // getSettings 메서드를 사용해 webSettings 객체를 참조한다.
        webSettings.setJavaScriptEnabled(true);
        // setJavaScriptEnable 값이 true로 설정되면 자바스크립트가 동작할 수 있는 환경이 된다.
        webView.setWebViewClient(new ViewClient());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // 버튼 클릭 시 사이트 로딩하기.
                webView.loadUrl(editText.getText().toString());
                // loadUrl 메서드를 사용하면 원격지의 웹페이지를 열거나 로컬에 저장된 HTML 파일을 열 수 있다.
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