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
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Details.NewDetailsActivity;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
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
* 新房的列表界面
* */
public class NewHouseActivity extends AppCompatActivity {
    private Context mContext=this;
    //初始化带刷新的列表listview
    private PullToRefreshListView newHouse_listView;
    private List<HomePagerBean> totalList=new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    private Handler han=new Handler();
    private ImageView toolBar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_house);
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
                newHouse_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        newHouse_listView= (PullToRefreshListView) findViewById(R.id.newHouse_listView);
        //初始化适配器
        homePageAdapter=new HomePageAdapter(mContext,totalList);
    }

    private void initClick() {
        //为listview设置适配器
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
                String developers = item.getDevelopers();
                String phone = item.getPhone();
                String lat = item.getLat();
                String lon = item.getLon();
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
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //网络访问方法获得数据
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
   /* //点击空白处隐藏键盘的方法
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
    public void onNewClick(View view) {
        switch (view.getId()) {
            case R.id.activity_new_house:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

        }
    }*/
}
