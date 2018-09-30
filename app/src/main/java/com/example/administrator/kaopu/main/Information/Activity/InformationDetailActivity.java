package com.example.administrator.kaopu.main.Information.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kaopu.R;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

public class InformationDetailActivity extends AppCompatActivity {
    private TextView textView_title,textView_purDate,textView_upinfo,textView_lowinfo;
    private ImageView imageView_img;
    private ImageView toolBar_back;
    private String title,upinfo,lowinfo,purDate;
    private Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initData();
    }


    private void initView() {
        textView_title= (TextView) findViewById(R.id.textView_title);
        textView_purDate= (TextView) findViewById(R.id.textView_purDate);
        textView_upinfo= (TextView) findViewById(R.id.textView_upinfo);
        textView_lowinfo= (TextView) findViewById(R.id.textView_lowinfo);
        imageView_img= (ImageView) findViewById(R.id.imageView_img);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);

    }
    private void initData() {
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
         title = extras.getString("title");
         upinfo = extras.getString("upinfo");
         lowinfo = extras.getString("lowinfo");
         purDate = extras.getString("purDate");
        List<String> img = (List<String>) extras.get("img");
        Glide.with(mContext).load(img.get(0)).into(imageView_img);
        textView_title.setText(title);
        textView_purDate.setText(purDate);
        textView_upinfo.setText("\u3000\u3000"+upinfo);
        textView_lowinfo.setText("\u3000\u3000"+lowinfo);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
