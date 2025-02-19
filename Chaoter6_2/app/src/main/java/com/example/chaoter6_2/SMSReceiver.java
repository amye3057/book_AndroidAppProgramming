package com.example.chaoter6_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSReceiver extends BroadcastReceiver {
    public static final String TAG = "SMSReciever";
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    // receivedDate 양식

    @Override
    public void onReceive(Context context, Intent intent) { // 파라미터로 들어오는 인텐트 객체 안에 SMS 데이터가 있음.
        // TODO: This method is called when the BroadcastReceiver is receiving

        Log.d(TAG,"OnReceive() 메서드 호출됨.");

        Bundle bundle = intent.getExtras(); // 번들 객체 안에 부가 데이터가 들어감
        SmsMessage[] messages = parseSmsMessage(bundle); // sms 메시지를 뽑아내기 위해 만든 메서드

        // 입력이 들어올 경우
        if(messages!=null && messages.length>0){
            String sender = messages[0].getOriginatingAddress(); // 발신자 번호 가져오기
            Log.i(TAG, "SMS sender : " +sender.toString());

            String contents = messages[0].getMessageBody(); // 문자 내용 가져오기
            Log.i(TAG,"SMS contents : "+contents.toString());

            Date receivedDate = new Date(messages[0].getTimestampMillis()); // SMS 받은 시각 가져오기
            Log.i(TAG,"SMS received date : "+receivedDate.toString());

            sendToActivity(context, sender, contents, receivedDate);
        }

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[]) bundle.get("pdus"); // 부가 데이터 중 pdus 가져오기
        SmsMessage[] messages = new SmsMessage[objs.length];

        int smsCount = objs.length;
        for(int i=0;i<smsCount;i++){
            // 단말 OS 버전에 따라 다른 방식으로 메서드 호출.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ // OS가 마시멜로(M) 버전과 같거나 이후 버전일 경우
                String format = bundle.getString("format");
                messages[i]=SmsMessage.createFromPdu((byte[]) objs[i], format);
            }
            else
                messages[i]=SmsMessage.createFromPdu((byte[]) objs[i]);
        }
        return messages;
    }

    private void sendToActivity(Context context, String sender, String contents, Date receivedDate){
        Intent myIntent = new Intent(context, SMSActicity.class); //SMSActivity로 인텐트 보내기

        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| // 브캐 수신자는 화면이 없으므로 화면을 생성하기 위해 추가
                Intent.FLAG_ACTIVITY_SINGLE_TOP| // 이미 메모리에 만든 SMSActivity가 있을 때 액티비티 중복생성하지 않기
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        myIntent.putExtra("sender",sender);
        myIntent.putExtra("contents",contents);
        myIntent.putExtra("receivedDate",format.format(receivedDate));

        context.startActivity(myIntent);
    }
}