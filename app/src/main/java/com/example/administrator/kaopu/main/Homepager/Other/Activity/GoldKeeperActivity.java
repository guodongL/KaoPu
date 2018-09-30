package com.example.administrator.kaopu.main.Homepager.Other.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Homepager.Other.Adapter.GoldKeeperAdapter;
import com.example.administrator.kaopu.main.Homepager.Other.Bean.GoldKeeperBean;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/*
* 金牌管家页面
* */
public class GoldKeeperActivity extends AppCompatActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private GridView gridView_goldKeeper;
    private GoldKeeperAdapter keeperAdapter;
    private List<GoldKeeperBean> totalList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_keeper);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
        HTTP_HomePage();
    }

    private void initView() {
        gridView_goldKeeper= (GridView) findViewById(R.id.gridView_goldKeeper);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        keeperAdapter=new GoldKeeperAdapter(mContext,totalList);
    }
    private void initClick() {
        gridView_goldKeeper.setAdapter(keeperAdapter);
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //网络访问方法获得数据
    public  void  HTTP_HomePage() {
        OkHttpUtils.get().url(UrlConfig.Path.GoldKeeper_URL).build().execute(new StringCallback() {
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
                totalList = parseJsonToMessageBean(jsonString);
                // homePageAdapter.reloadGridView(totalList,true);
                if (totalList == null) {
                    totalList = new ArrayList<>();
                }
                gridView_goldKeeper.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keeperAdapter.reloadGridView(totalList, true);

                    }
                }, 1000);
            }
        });
    }
    //自定义通过gson解析获得数据数组的方法
    public List<GoldKeeperBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<GoldKeeperBean> list = gson.fromJson(jsonString, new TypeToken<List<GoldKeeperBean>>() {
        }.getType());
        return list;
    }
}
