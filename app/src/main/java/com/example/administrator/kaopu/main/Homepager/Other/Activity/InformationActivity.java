package com.example.administrator.kaopu.main.Homepager.Other.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Homepager.Other.Adapter.InformationAdapter;
import com.example.administrator.kaopu.main.Homepager.Other.Bean.InformationBean;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
import com.example.administrator.kaopu.main.Information.Activity.InformationDetailActivity;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
/*
* 资讯列表界面
* */
public class InformationActivity extends AppCompatActivity {
    private PullToRefreshListView information_listView;
    private Context mContext=this;
    private Handler han=new Handler();
    private ImageView toolBar_back;
    private List<InformationBean> list=new ArrayList<>();
    private InformationAdapter inforAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
        autoRefresh();
        HTTP_Information();

    }
    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                information_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        information_listView= (PullToRefreshListView) findViewById(R.id.information_listView);
        inforAdapter=new InformationAdapter(mContext,list);
    }

    private void initClick() {
        information_listView.setAdapter(inforAdapter);
        information_listView.setMode(PullToRefreshBase.Mode.BOTH);
        information_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_Information();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                information_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        information_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
        information_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InformationBean item = (InformationBean) inforAdapter.getItem(position - 1);
                List<String> img = item.getImg();
                String title = item.getTitle();
                String upinfo = item.getUpinfo();
                String lowinfo = item.getLowinfo();
                String purDate = item.getPurDate();
                Intent intent=new Intent();
                intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("upinfo", upinfo);
                bundle.putString("lowinfo", lowinfo);
                bundle.putString("purDate", purDate);
                intent.putExtras(bundle);
                intent.setClass(mContext, InformationDetailActivity.class);
                startActivity(intent);
            }
        });
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
                information_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        information_listView.onRefreshComplete();
                    }
                }, 1000);
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
                information_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        information_listView.onRefreshComplete();
                        inforAdapter.reloadGridView(list, true);
                        if (list != null) {
                            if (list.size() > 0) {
                                information_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
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
