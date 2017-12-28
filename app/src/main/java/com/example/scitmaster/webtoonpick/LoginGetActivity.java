package com.example.scitmaster.webtoonpick;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SCITMaster on 2017-12-27.
 */

public class LoginGetActivity extends AppCompatActivity {

    EditText userId, password;
    String u, p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.password);
    }

    public void connect(View view){
        int id = view.getId();
        u = userId.getText().toString().trim();
        p = password.getText().toString().trim();

        if(p.length() == 0 || u.length() == 0 ){
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        new LoginThread().start();

    }

    class LoginThread extends Thread{
        String addr = "http://10.10.12.71:8889/spring/login?userId="+u+"&password="+p;

        public void run(){
            try {
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);

                StringBuilder sb = new StringBuilder();
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    int ch;

                    while ((ch = in.read()) != -1){
                        sb.append((char)ch);
                    }
                    in.close();
                }

                Log.v("Response Conde =>", conn.getResponseCode()+"");
                Message message = handler.obtainMessage();
                message.obj = sb.toString();
                handler.sendMessage(message);
                conn.disconnect();


            }catch (Exception e){

            }
        }
    }
    // TextView에 데이터를 출력하는 역할 담당
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = (String)msg.obj;

            if(message.equals("SUCCESS")){
                Toast.makeText(LoginGetActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginGetActivity.this,"로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
