package com.example.chapter10_6;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        recyclerView = findViewById(R.id.recyclerView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void makeRequest() {
        String url = editText.getText().toString();

        StringRequest request = new StringRequest(
                Request.Method.GET, /* 파라미터 1 */
                url, /* 파라미터 2 */
                new Response.Listener<String>() { /* 파라미터 3 */
                    @Override
                    public void onResponse(String response) {
                        Log.d("MainActivity","응답 -> "+response);
                        processResponse(response); // 응답을 받을 시 processResponse 메서드 호출
                    }
                },
                new Response.ErrorListener() { /* 파라미터 4 */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivity","에러 -> "+error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false); // 이전 응답 결과 사용 x
        requestQueue.add(request); // 요청 큐에 요청 객체 넣기
        Log.d("MainActivity", "요청 보냄.");
    }

    private void processResponse(String response) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);
        Log.d("MainActivity", "영화 건수 : "+movieList.boxOfficeResult.dailyBoxOfficeList.size());

        for(int i=0;i<movieList.boxOfficeResult.dailyBoxOfficeList.size();i++) {
            MyMovie movie = movieList.boxOfficeResult.dailyBoxOfficeList.get(i);
            adapter.addItem(movie);
        }
        adapter.notifyDataSetChanged(); // 어댑터에 모두 추가 후 notifyDataSetChanged 메서드를 호출해야 변경사항이 반영됨.
    }
}