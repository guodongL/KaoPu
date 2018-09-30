package com.example.administrator.kaopu.main.Homepager.Other.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.kaopu.R;
import com.githang.statusbar.StatusBarCompat;
/*
* 海外地产界面
* */
public class OverseasActivity extends AppCompatActivity {
private ImageView toolBar_back;
    private Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overseas);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
    }

    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
    }

    private void initClick() {
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
