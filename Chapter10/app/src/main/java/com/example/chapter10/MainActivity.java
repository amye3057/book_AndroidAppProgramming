package com.example.chapter10;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextText);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Button buttonSend = findViewById(R.id.button);
        Button buttonStart = findViewById(R.id.button2);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String data = editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send(data); // send 메서드 작성
                    }
                }).start();
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer(); // startServer 메서드 작성
                    }
                }).start();
            }
        });

    }

    private void send(String data) {
        try{
            int portNum = 5001; // 포트 5001 사용
            Socket socket = new Socket("localhost",portNum); // 접속할 IP 주소 : localhost
            printClientLog("소켓 연결함."); // printClientLog 메서드 작성

            ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
            outstream.writeObject(data); // OutputStream 객체는 writeObject, flush 메서드를 사용할 수 있다.
            outstream.flush(); // 남아있는 데이터를 방출한다.
            printClientLog("데이터 전송함.");

            ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
            printClientLog("서버로부터 받음 : "+instream.readObject());
            // InputStream 객체는 readObject 메서드를 사용할 수 있다. (읽기)

            socket.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void printClientLog(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView1.append(data+"\n");
            }
        });
    }

    private void startServer() {
        try{
            int portNum = 5001;
            ServerSocket serverSocket = new ServerSocket(portNum);
            printServerLog("서버 시작함 : "+portNum);

            while(true){
                Socket socket = serverSocket.accept();
                InetAddress clientHost = socket.getLocalAddress();
                int clientPort = socket.getPort();
                printServerLog("클라이언트 연결됨 : "+clientHost+" : "+clientPort);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object object = inputStream.readObject();
                printServerLog("데이터 받음 : "+object);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(object + " from Server.");
                outputStream.flush();
                printServerLog("데이터 보냄.");

                socket.close();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void printServerLog(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView2.append(data+"\n");
            }
        });
    }


}