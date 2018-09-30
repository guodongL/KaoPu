package com.example.administrator.kaopu.main.Homepager.Other.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Details.NewDetailsActivity;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
import com.example.administrator.kaopu.main.Recommend.Other.RentingAdapter;
import com.example.administrator.kaopu.main.Recommend.Other.RentingBean;
import com.example.administrator.kaopu.main.Recommend.Other.SecondHouseBean;
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
* 租房的列表界面
* */
public class RentingActivity extends AppCompatActivity {

    private Context mContext=this;
    //初始化带刷新的列表listview
    private PullToRefreshListView rentingHouse_listView;
    private List<RentingBean> totalList=new ArrayList<>();
    private RentingAdapter homePageAdapter;
    private Handler han=new Handler();
    private ImageView toolBar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
        autoRefresh();
        HTTP_HomePage();

    }
    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                rentingHouse_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        rentingHouse_listView= (PullToRefreshListView) findViewById(R.id.rentingHouse_listView);
        homePageAdapter=new RentingAdapter(mContext,totalList);
    }

    private void initClick() {
        rentingHouse_listView.setAdapter(homePageAdapter);
        rentingHouse_listView.setMode(PullToRefreshBase.Mode.BOTH);
        rentingHouse_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_HomePage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                rentingHouse_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rentingHouse_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
        rentingHouse_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RentingBean item = (RentingBean) homePageAdapter.getItem(position-1);
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
                bundle.putString("type", "3");
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
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void  HTTP_HomePage() {
        OkHttpUtils.get().url(UrlConfig.Path.RentingHouse_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                rentingHouse_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rentingHouse_listView.onRefreshComplete();
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
                rentingHouse_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rentingHouse_listView.onRefreshComplete();
                        homePageAdapter.reloadGridView(totalList, true);
                        if (totalList != null) {
                            if (totalList.size() > 0) {
                                rentingHouse_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    //自定义通过gson解析获得数据数组的方法
    public List<RentingBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<RentingBean> list = gson.fromJson(jsonString, new TypeToken<List<RentingBean>>() {
        }.getType());
        return list;
    }
 /*   //点击空白处隐藏键盘的方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                editText_findHouse.setCursorVisible(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }else {
                editText_findHouse.setCursorVisible(true);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    //下面方法是解决防止进入界面键盘自动弹出
    public void onRentingClick(View view) {
        switch (view.getId()) {
            case R.id.activity_new_house:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }*/
}
