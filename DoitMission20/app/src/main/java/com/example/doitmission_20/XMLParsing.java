package com.example.doitmission_20;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class XMLParsing extends AsyncTask<Object, Object, ArrayList<RSS>> {

    // News RSS 주소
    private String rssURL; // = "https://www.mk.co.kr/rss/30000001/";

    public XMLParsing(String s) {
        this.rssURL = s;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<RSS> doInBackground(Object... objects) {
        ArrayList<RSS> list = new ArrayList<RSS>();

        try{
            URL url = new URL(rssURL);
            String title="";
            String description="";

            // XmlParser로 RSS의 값들을 가져온다.
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            BufferedInputStream buf = new BufferedInputStream(url.openStream());
            parser.setInput(buf, null);

            String tag=""; // tag를 구분하기 위한 값. (ex. <title>, <item>, <discription>, <date>...)
            boolean isItemTag = false; // <item> 태그를 구분하기 위한 값

            // getEventType() : 문서 시작&끝, 태그 시작&끝, 태그의 내용을 표시하는 값을 반환
            int parserEvent = parser.getEventType();

            while(parserEvent!=XmlPullParser.END_DOCUMENT){ // 문서가 끝날 때까지 반복
                switch(parserEvent){
                    // TAG 시작일 경우
                    case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if(tag.equals("item")){ // TAG가 <item>일 경우
                                isItemTag = true;
                            }
                            break;
                    // TAG 안의 문자열일 경우
                    case XmlPullParser.TEXT:
                        if(isItemTag){
                            //Log.d("TAG",tag);
                            if(tag.equals("title"))
                                title = parser.getText();
                            else if(tag.equals("description"))
                                description = parser.getText();
                            // 태그 확인 후 제목, 설명에 맞게 값을 넣어준다.
                        }
                        break;
                    // TAG 종료일 경우
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("item")){ // item 태그 종료 확인
                            list.add(new RSS(title, description));
                            title="";
                            description="";
                            isItemTag=false;
                        }
                        tag=""; //태그 값을 초기화하라는데 굳이 할 필요 있나? <- 주석처리 해놨더니 응.. 안되네
                        // while문 도는 동안 tag가 초기에 초기화 되는 걸로 착각했음.
                        break;
                }
                // next() : 다음 TAG로 이동
                parserEvent = parser.next();
            }

        }catch(Exception e){
            Log.d("RSS","RSS를 가져오는 동안 문제 발생 : "+e);
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<RSS> rsses) {
        super.onPostExecute(rsses);
    }

    // RSS 의 내용을 읽어오는 메서드
    public ArrayList<RSS> getData() {
        ArrayList<RSS> list = new ArrayList<RSS>();
        try {
            list = execute().get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {}
        return list;
    }
}
