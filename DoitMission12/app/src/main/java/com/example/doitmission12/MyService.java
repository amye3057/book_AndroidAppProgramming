package com.example.doitmission12;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent==null)
            return Service.START_STICKY;
        else
            processCommand(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent){
        String text = intent.getStringExtra("key");
        // 확인용
        if(text!=null)
          Log.d("service",text!=null?text:"null");
        Intent activityIntent = new Intent("com.example.CUSTOM_BROADCAST");
        activityIntent.putExtra("key",text+"(service)");
        sendBroadcast(activityIntent);
    }
}