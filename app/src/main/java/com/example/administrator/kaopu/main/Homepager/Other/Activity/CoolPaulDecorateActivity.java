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
* 靠谱装饰页面
* */
public class CoolPaulDecorateActivity extends AppCompatActivity {
private Context mContext=this;
    private ImageView toolBar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_paul_decorate);
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
