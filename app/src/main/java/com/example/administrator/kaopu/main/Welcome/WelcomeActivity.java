package com.example.administrator.kaopu.main.Welcome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.main.This.MainActivity;
import com.githang.statusbar.StatusBarCompat;

public class WelcomeActivity extends AppCompatActivity {
    private Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        Handler handler=new Handler();
        //开启子线程延时2秒跳转页面
        handler.postDelayed(new NewHandler(),1000);
    }
    class NewHandler implements Runnable{

        @Override
        public void run() {
                //进行页面跳转
                Intent intent = new Intent();
                intent.setClass(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
        }
    }
}
