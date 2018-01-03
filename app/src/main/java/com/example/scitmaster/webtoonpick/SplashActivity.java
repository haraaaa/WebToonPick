package com.example.scitmaster.webtoonpick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by SCITMaster on 2017-12-28.
 */

public class SplashActivity extends Activity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.e("bsos1202", "SplashActivity income!!");
        intent = new Intent(this, MainActivity.class);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000); // 3초 후 이미지를 닫습니다
    }
}