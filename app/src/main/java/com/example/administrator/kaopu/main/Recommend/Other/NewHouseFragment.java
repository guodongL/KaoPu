package com.example.administrator.kaopu.main.Recommend.Other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Details.NewDetailsActivity;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
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
 * Created by Administrator on 2017/8/31.
 */

public class NewHouseFragment extends Fragment {
    private Context mContext;
    //初始化带刷新的列表listview
    private PullToRefreshListView newHouse_listView;
    private List<HomePagerBean> totalList=new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    private Handler han=new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        homePageAdapter=new HomePageAdapter(mContext,totalList);
        autoRefresh();
        HTTP_HomePage();
    }
    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                newHouse_listView.setRefreshing(true);
            }
        },100);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newhouse_fragment, container, false);
        newHouse_listView = (PullToRefreshListView) view.findViewById(R.id.newHouse_listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newHouse_listView.setAdapter(homePageAdapter);
        newHouse_listView.setMode(PullToRefreshBase.Mode.BOTH);
        newHouse_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_HomePage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                newHouse_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newHouse_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
        newHouse_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomePagerBean item = (HomePagerBean) homePageAdapter.getItem(position-1);
                List<String> img = item.getImg();
                List<String> imgHouse = item.getImgHouse();
                String itemId = item.getId();
                String name = item.getName();
                String address = item.getAddress();
                String fitment = item.getFitment();
                String familyType = item.getFamilyType();
                String openTime = item.getOpenTime();
                String price = item.getPrice();
                String lat = item.getLat();
                String lon = item.getLon();
                String developers = item.getDevelopers();
                String phone = item.getPhone();
                Intent intent=new Intent();
                intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                intent.putStringArrayListExtra("imgHouse", (ArrayList<String>) imgHouse);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", itemId);
                bundle.putString("lat", lat);
                bundle.putString("lon", lon);
                bundle.putString("name", name);
                bundle.putString("address", address);
                bundle.putString("fitment", fitment);
                bundle.putString("familyType", familyType);
                bundle.putString("openTime", openTime);
                bundle.putString("price", price);
                bundle.putString("type", "1");
                bundle.putString("developers", developers);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                intent.setClass(mContext, NewDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    public  void  HTTP_HomePage() {
        OkHttpUtils.get().url(UrlConfig.Path.HomePager_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络访问失败，请连接网络",Toast.LENGTH_LONG).show();
                newHouse_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newHouse_listView.onRefreshComplete();
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
                newHouse_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newHouse_listView.onRefreshComplete();
                        homePageAdapter.reloadGridView(totalList, true);
                        if (totalList != null) {
                            if (totalList.size() > 0) {
                                newHouse_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    //自定义通过gson解析获得数据数组的方法
    public List<HomePagerBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<HomePagerBean> list = gson.fromJson(jsonString, new TypeToken<List<HomePagerBean>>() {
        }.getType());
        return list;
    }
}
