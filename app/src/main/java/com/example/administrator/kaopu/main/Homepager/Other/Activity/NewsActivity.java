package com.example.administrator.kaopu.main.Homepager.Other.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Homepager.Other.Bean.InformationBean;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class NewsActivity extends AppCompatActivity {
    private List<InformationBean> list=new ArrayList<>();
    private TextView textView_title,textView_purDate,textView_upinfo,textView_lowinfo;
    private ImageView imageView_img;
    private ImageView toolBar_back;
    private Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        HTTP_Information();
    }

    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        textView_title= (TextView) findViewById(R.id.textView_title);
        textView_purDate= (TextView) findViewById(R.id.textView_purDate);
        textView_upinfo= (TextView) findViewById(R.id.textView_upinfo);
        textView_lowinfo= (TextView) findViewById(R.id.textView_lowinfo);
        imageView_img= (ImageView) findViewById(R.id.imageView_img);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public  void  HTTP_Information() {
        OkHttpUtils.get().url(UrlConfig.Path.Information_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络访问失败，请连接网络",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                list = parseJsonToMessageBean(jsonString);
                // homePageAdapter.reloadGridView(totalList,true);
                if (list == null) {
                    list = new ArrayList<>();
                }
                List<String> img = list.get(0).getImg();
                Glide.with(mContext).load(img.get(0)).into(imageView_img);
                textView_title.setText(list.get(0).getTitle());
                textView_purDate.setText(list.get(0).getPurDate());
                textView_upinfo.setText(list.get(0).getUpinfo());
                textView_lowinfo.setText(list.get(0).getLowinfo());
            }
        });
    }
    //自定义通过gson解析获得数据数组的方法
    public List<InformationBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<InformationBean> list = gson.fromJson(jsonString, new TypeToken<List<InformationBean>>() {
        }.getType());
        return list;
    }
}
