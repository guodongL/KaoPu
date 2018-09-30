package com.example.administrator.kaopu.main.Shop.This;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Recommend.Other.ApartmentAdapter;
import com.example.administrator.kaopu.main.Recommend.Other.ApartmentBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/28.
 */

public class StoreFragment extends Fragment {
    private PullToRefreshListView stroeFragment_listView;
    private Context mContext;
    private List<StoreBean> totalList=new ArrayList<>();
    private StoreAdapter storeAdapter;
    private Handler han=new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        storeAdapter=new StoreAdapter(mContext,totalList);
        autoRefresh();
        HTTP_Store();
    }
    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                stroeFragment_listView.setRefreshing(true);
            }
        },100);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);
        stroeFragment_listView= (PullToRefreshListView) view.findViewById(R.id.stroeFragment_listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stroeFragment_listView.setAdapter(storeAdapter);
        stroeFragment_listView.setMode(PullToRefreshBase.Mode.BOTH);
        stroeFragment_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_Store();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                stroeFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stroeFragment_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
    }

    public  void  HTTP_Store() {
        OkHttpUtils.get().url(UrlConfig.Path.Store_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络访问失败，请连接网络",Toast.LENGTH_LONG).show();
                stroeFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stroeFragment_listView.onRefreshComplete();
                    }
                }, 1000);
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
                stroeFragment_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stroeFragment_listView.onRefreshComplete();
                        storeAdapter.reloadGridView(totalList, true);
                        if (totalList != null) {
                            if (totalList.size() > 0) {
                                stroeFragment_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    //自定义通过gson解析获得数据数组的方法
    public List<StoreBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<StoreBean> list = gson.fromJson(jsonString, new TypeToken<List<StoreBean>>() {
        }.getType());
        return list;
    }
}
