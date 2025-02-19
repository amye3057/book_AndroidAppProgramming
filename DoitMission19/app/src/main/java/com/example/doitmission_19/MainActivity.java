package com.example.doitmission_19;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    WebView webView;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        textView = findViewById(R.id.textView);

        // 웹뷰 세팅
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest(); // textView에 데이터 출력하기
                new Handler().postDelayed(() -> {
                    showWebView(); // 웹뷰에 사이트 나타내기
                }, 1000);
            }
        });

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    private void makeRequest() {
        String url = editText.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.append(response+"\n");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.append("에러->"+error.getMessage()+"\n");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        //textView.append("요청보냄.\n");
    }

    private void showWebView() {
        String data = textView.getText().toString();
        //webView.loadUrl(url);
        webView.loadDataWithBaseURL(null,data,"text/html","utf-8",null);
    }

    /*
    public void showWebView(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.loadDataWithBaseURL(null,data,"text/html","utf-8",null);
            }
        });
    }*/

}