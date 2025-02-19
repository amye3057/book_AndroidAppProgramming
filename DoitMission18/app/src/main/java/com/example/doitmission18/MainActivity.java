package com.example.doitmission18;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    TextView textNum;
    boolean turn = false;
    boolean slideshow = true;
    Calendar cal = new GregorianCalendar();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    Handler handler;
    Bitmap bitmaps[] = new Bitmap[2];
    String dates[] = new String[2];

    public ImageView imageViews[] = new ImageView[4];
    public TextView textViews[] = new TextView[4];

    LinearLayout panel1;
    LinearLayout panel2;

    Animation visibleLeft;
    Animation invisibleLeft;
    int position=1;
    int totalpages = 0;
    int pages = 0;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoPermissions.Companion.loadAllPermissions(this,101);

        imageViews[0] = findViewById(R.id.imageView1);
        imageViews[1] = findViewById(R.id.imageView11);
        imageViews[2] = findViewById(R.id.imageView2);
        imageViews[3] = findViewById(R.id.imageView22);

        textViews[0] = findViewById(R.id.textView1);
        textViews[1] = findViewById(R.id.textView11);
        textViews[2] = findViewById(R.id.textView2);
        textViews[3] = findViewById(R.id.textView22);

        panel1 = findViewById(R.id.pannel);
        panel2 = findViewById(R.id.pannel2);
        textNum = findViewById(R.id.textView_n);

        visibleLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear);
        invisibleLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.disappear);
        PanelAnimationListener animationListener = new PanelAnimationListener();
        invisibleLeft.setAnimationListener(animationListener);
        visibleLeft.setAnimationListener(animationListener);

        final MyThread myThread = new MyThread();
        handler = new Handler();
        myThread.start();
    }

    class MyThread extends Thread{
        public void run(){
            //커서 얻기
            getCurse();
            while(slideshow){
                if(!turn){
                    handler.post(new Runnable() {
                        @Override
                        //패널 두개를 애니메이션으로 돌리면서 그 직전에 image랑 text를 넣는 형식으로함 (더이상은 모르겠음)
                        public void run() {
                            //커서에서 PATH랑 DATE얻기
                            getLastImage();

                            //이미지 뷰, 텍스트 세팅
                            for(int i=0; i<2; i++){
                                //실제 이미지 그대로 보여주는 방법
                                //File files = new File(paths[i]);
                                //if(files.exists()){
                                // Bitmap myBitmap = BitmapFactory.decodeFile(files.getAbsolutePath());
                                // imageViews[i+2].setImageBitmap(myBitmap);
                                imageViews[i+2].setImageBitmap(bitmaps[i]);
                                textViews[i+2].setText(dates[i]);
                                //}
                            }
                            panel1.startAnimation(invisibleLeft);
                            panel2.startAnimation(visibleLeft);
                            turn = !turn;
                            textNum.setText(pages + "/" + totalpages);
                        }
                    });
                }
                else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getLastImage();

                            for(int i=0; i<2; i++){
                                //File files = new File(paths[i]);
                                //if(files.exists()){
                                //Bitmap myBitmap = BitmapFactory.decodeFile(files.getAbsolutePath());
                                //imageViews[i].setImageBitmap(myBitmap);
                                imageViews[i].setImageBitmap(bitmaps[i]);
                                textViews[i].setText(dates[i]);
                                //}
                            }
                            panel2.startAnimation(invisibleLeft);
                            panel1.startAnimation(visibleLeft);
                            turn = !turn;
                            textNum.setText(pages + "/" + totalpages);
                        }
                    });
                }
                try{
                    Thread.sleep(2000);
                } catch(Exception e){};
            }
        }
    }

    //날짜 형식
    public void getDate(long time, int position){
        cal.setTimeInMillis(time);
        Date d = cal.getTime();
        dates[position] = format.format(d);
    }

    //썸네일 띄우기
    public void getThumbnail(long id, int position){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = true;
        options.inSampleSize = 5; // 이 숫자가 커질수록 사진 크기가 작아져서 속도가 빨라진다
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmaps[position] = MediaStore.Images.Thumbnails.getThumbnail( getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND,options);
    }

    //핸드폰 내 이미지파일 cursor로 찾기
    public void getCurse(){
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID, //사진의 아이디
                //MediaStore.Images.ImageColumns.DATA,	//사진 경로
                MediaStore.Images.ImageColumns.DATE_TAKEN	//사진 찍은 날짜
        };
        cursor = getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");	//날짜 역순으로 정렬
        totalpages = cursor.getCount()/2;
    }

    //이미지뷰 ,텍스트뷰에 들어갈 내용 만들기
    public void getLastImage() {
        long datessec;
        long id;
        // Put it in the image view
        if (cursor.moveToNext()) {
            //paths[0]  = cursor.getString(1);

            id = cursor.getLong(0);
            getThumbnail(id,0); //id로 썸네일 구하기

            datessec = cursor.getLong(1);
            getDate(datessec,0);    //date구하기
        }
        if(cursor.moveToNext()){
            //paths[1]  = cursor.getString(1);
            id = cursor.getLong(0);
            getThumbnail(id,1);

            datessec = cursor.getLong(1);
            getDate(datessec,1);
        }
        pages++;
    }

    //권한승인
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        AutoPermissions.Companion.parsePermissions(this,requestCode, permissions,this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
    }

    @Override
    public void onGranted(int i, String[] strings) {
    }

    //애니메이션 리스너
    private class PanelAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            panel2.setVisibility(View.VISIBLE);
            panel1.setVisibility(View.VISIBLE);
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            // 이 메서드 내의 코드 굳이 필요한가? 이동하면 어차피 안보일텐데
            if(turn){
                panel1.setVisibility(View.INVISIBLE);
            }else{
                panel2.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}
}