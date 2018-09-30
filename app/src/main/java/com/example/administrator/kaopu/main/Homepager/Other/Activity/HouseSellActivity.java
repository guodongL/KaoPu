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
import com.example.administrator.kaopu.main.Details.NewDetailsActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Adapter.HouseSellAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
import com.example.administrator.kaopu.main.Recommend.Other.RentingBean;
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
* 热销房源界面
* */
public class HouseSellActivity extends AppCompatActivity {
    private PullToRefreshListView houseSell_listView;
    private Context mContext=this;
    private List<HotHouseBean> totalList=new ArrayList<>();
    private ImageView toolBar_back;
    private HouseSellAdapter homePageAdapter;
    private Handler han=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_sell);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
        autoRefresh();
        HTTP_HomePage();
        initData();

    }


    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                houseSell_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        houseSell_listView= (PullToRefreshListView) findViewById(R.id.houseSell_listView);
        homePageAdapter=new HouseSellAdapter(mContext,totalList);

    }
    private void initClick() {
        houseSell_listView.setAdapter(homePageAdapter);
        houseSell_listView.setMode(PullToRefreshBase.Mode.BOTH);
        houseSell_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
               houseSell_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HTTP_HomePage();
                        houseSell_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                houseSell_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        houseSell_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
    toolBar_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }
    private void initData() {
       /* if (newList!=null&&newList.size()!=0&&secondList.size()!=0&&secondList!=null) {
            for (int i = 0; i <= 3; i++) {
                RentingBean homePagerBean = newList.get(i);
                totalList.add(homePagerBean);
            }
            for (int i = 0; i <= 3; i++) {
                RentingBean homePagerBean = secondList.get(i);
                totalList.add(homePagerBean);
            }
        }
                        if (totalList.size() == 8) {
                            autoRefresh();
                            homePageAdapter.reloadGridView(totalList, true);
                        }else {
                           //initData();
                        }*/


        houseSell_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotHouseBean item = (HotHouseBean) homePageAdapter.getItem(position-1);
                String type = item.getType();
                List<String> img = item.getImg();
                String itemId = item.getId();
                String name = item.getName();
                String address = item.getAddress();
                String fitment = item.getFitment();
                String info = item.getInfo();
                String size = item.getSize();
                String price = item.getPrice();
                String floors = item.getFloors();
                String payment = item.getPayment();
                String phone = item.getPhone();
                String lat = item.getLat();
                String lon = item.getLon();
                Intent intent=new Intent();
                intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", itemId);
                bundle.putString("lat", lat);
                bundle.putString("lon", lon);
                bundle.putString("payment", payment);
                bundle.putString("type",type);
                bundle.putString("name", name);
                bundle.putString("address", address);
                bundle.putString("fitment", fitment);
                bundle.putString("info", info);
                bundle.putString("size", size);
                bundle.putString("price", price);
                bundle.putString("floors", floors);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                intent.setClass(mContext, NewDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    public  void  HTTP_HomePage() {
        OkHttpUtils.get().url(UrlConfig.Path.HotHouse_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络访问失败，请连接网络",Toast.LENGTH_LONG).show();
                houseSell_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        houseSell_listView.onRefreshComplete();
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
                if (totalList == null) {
                    totalList = new ArrayList<>();
                }
                houseSell_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        houseSell_listView.onRefreshComplete();
                        homePageAdapter.reloadGridView(totalList, true);
                    }
                }, 1500);
               /* // homePageAdapter.reloadGridView(totalList,true);
                if (newList == null) {
                    newList = new ArrayList<>();
                }*/
            }
        });
    /*    OkHttpUtils.get().url(UrlConfig.Path.RentingHouse_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                houseSell_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        houseSell_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString = s;
                if (HTTP_GD.IsOrNot_Null(jsonString)) {
                    return;
                }
                secondList = parseJsonToMessageBean(jsonString);
                if (secondList.size()!=0) {
                    for (int i = 0; i <= 3; i++) {
                        RentingBean homePagerBean = secondList.get(i);
                        secondtwoList.add(homePagerBean);
                    }
                }
            *//*    // homePageAdapter.reloadGridView(totalList,true);
                if (secondList == null) {
                    secondList = new ArrayList<>();
                }*//*
            }
        });
  *//*      han.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (newList.size()!=0&&secondList.size()!=0) {
                    homePageAdapter.reloadGridView(totalList, true);
                }
            }
        },200);*//*
        totalList.addAll(newtwoList);
        totalList.addAll(secondtwoList);
        houseSell_listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                homePageAdapter.reloadGridView(totalList, true);
                houseSell_listView.onRefreshComplete();
            }
        }, 1000);*/
    }
    //自定义通过gson解析获得数据数组的方法
    public List<HotHouseBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<HotHouseBean> list = gson.fromJson(jsonString, new TypeToken<List<HotHouseBean>>() {
        }.getType());
        return list;
    }
}
