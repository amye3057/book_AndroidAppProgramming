package com.example.chapter6_1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    // 생성자 메서드
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Binding : 서비스가 서버 역할을 하면서 액티비티와 연결될 수 있도록 만드는 것
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate() 호출됨");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand() 호출됨");

        if(intent == null) // 서비스는 시스템에 의해 자동으로 재시작될 수 있기 때문에 인텐트가 null인 경우도 검사하는 것.
            return Service.START_STICKY; // 이 값을 반환하면 시스템이 비정상 종료되었다는 의미.
        else
            processCommand(intent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent){
        // 인텐트에서 부가 데이터 가져오기
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");

        Log.d(TAG,"command : "+command+", name : "+name);

        for(int i=0;i<5;i++){ // 5초 동안 1초에 한번씩 로그 출력.
            try{
                Thread.sleep(1000); // 1000ms = 1초
            } catch(Exception e){};
            Log.d(TAG,"Waiting "+i+" seconds.");
        }

        // 액티비티로 인텐트 넘기기
        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | // startActivity 메서드 호출 시 새로운 태스크 생성
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        showIntent.putExtra("command", "show");
        showIntent.putExtra("name",name+" from service.");
        startActivity(showIntent); // MainActivity 쪽으로 인텐트 객체 전달
    }
}