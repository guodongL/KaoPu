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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Details.NewDetailsActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Adapter.SearchAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
import com.example.administrator.kaopu.main.Recommend.Other.ApartmentBean;
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

public class SearchActivity extends AppCompatActivity {
    private Context mContext=this;
    private List<SearchBean> totalList=new ArrayList<>();
    private List<HomePagerBean> list=new ArrayList<>();
    private EditText editText_search;
    private Button search_button;
    private ImageView textView_sure;
    private Button button_wanda;
    private Button button_aoyuan;
    private Button button_baoli;
    private PullToRefreshListView search_listView;
    private LinearLayout linear_search;
    private SearchAdapter rentingAdapter;
    private Handler han=new Handler();
    private String search;
    private Button button_back;
    private LinearLayout line_noFind;
    private HomePageAdapter homePageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
    }
    //下拉刷新列表的方法
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                search_listView.setRefreshing(true);
            }
        },100);
    }
    private void initView() {
        line_noFind= (LinearLayout) findViewById(R.id.line_noFind);
        button_back= (Button) findViewById(R.id.button_back);
        button_baoli= (Button) findViewById(R.id.button_baoli);
        linear_search= (LinearLayout) findViewById(R.id.linear_search);
        editText_search= (EditText) findViewById(R.id.editText_search);
        button_wanda= (Button) findViewById(R.id.button_wanda);
        button_aoyuan= (Button) findViewById(R.id.button_aoyuan);
        search_button= (Button) findViewById(R.id.search_button);
        textView_sure= (ImageView) findViewById(R.id.textView_sure);
        search_listView= (PullToRefreshListView) findViewById(R.id.search_listView);
        search_listView.setVisibility(View.INVISIBLE);
        rentingAdapter=new SearchAdapter(mContext,totalList);
        homePageAdapter=new HomePageAdapter(mContext,list);
    }
    private void initClick() {
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_aoyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aoyuan = button_aoyuan.getText().toString();
                editText_search.setText(aoyuan);
            }
        });
        button_baoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baoli = button_baoli.getText().toString();
                editText_search.setText(baoli);
            }
        });
        button_wanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wanda = button_wanda.getText().toString();
                editText_search.setText(wanda);
            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = editText_search.getText().toString();
                if (search.equals("")){
                    Toast.makeText(mContext,"请输入正确内容",Toast.LENGTH_LONG).show();
                }else {
                   autoRefresh();
                }
            }
        });
        search_listView.setAdapter(rentingAdapter);
        search_listView.setMode(PullToRefreshBase.Mode.BOTH);
        search_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_HomePage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                search_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        search_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
        search_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchBean item = (SearchBean) rentingAdapter.getItem(position-1);
                String type = item.getType();
                if (type.equals("1")){
                    HomePagerBean item1 = (HomePagerBean) homePageAdapter.getItem(position-1);
                    List<String> img = item1.getImg();
                    List<String> imgHouse = item1.getImgHouse();
                    String itemId = item1.getId();
                    String name = item.getName();
                    String address = item.getAddress();
                    String fitment = item.getFitment();
                    String familyType = item.getFamilyType();
                    String openTime = item1.getOpenTime();
                    String price = item.getPrice();
                    String phone = item.getPhone();
                    String lat = item.getLat();
                    String lon = item.getLon();
                    String developers = item1.getDevelopers();
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
                }else if (type.equals("2")){
                    List<String> img = item.getImg();
                    String itemId = item.getId();
                    String name = item.getName();
                    String address = item.getAddress();
                    String fitment = item.getFitment();
                    String info = item.getInfo();
                    String size = item.getSize();
                    String price = item.getPrice();
                    String floors = item.getFloors();
                    String lat = item.getLat();
                    String lon = item.getLon();
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                    Bundle bundle = new Bundle();
                    bundle.putString("itemId", itemId);
                    bundle.putString("lat", lat);
                    bundle.putString("lon", lon);
                    bundle.putString("type", "2");
                    bundle.putString("name", name);
                    bundle.putString("address", address);
                    bundle.putString("fitment", fitment);
                    bundle.putString("info", info);
                    bundle.putString("size", size);
                    bundle.putString("price", price);
                    bundle.putString("floors", floors);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, NewDetailsActivity.class);
                    startActivity(intent);
                }else if (type.equals("3")){
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
                    String lat = item.getLat();
                    String lon = item.getLon();
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", lat);
                    bundle.putString("lon", lon);
                    bundle.putString("itemId", itemId);
                    bundle.putString("payment", payment);
                    bundle.putString("type", "3");
                    bundle.putString("name", name);
                    bundle.putString("address", address);
                    bundle.putString("fitment", fitment);
                    bundle.putString("info", info);
                    bundle.putString("size", size);
                    bundle.putString("price", price);
                    bundle.putString("floors", floors);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, NewDetailsActivity.class);
                    startActivity(intent);
                }else if (type.equals("4")){
                    List<String> img = item.getImg();
                    String itemId = item.getId();
                    String name = item.getName();
                    String address = item.getAddress();
                    String fitment = item.getFitment();
                    String info = item.getInfo();
                    String size = item.getSize();
                    String price = item.getPrice();
                    String floors = item.getFloors();
                    String lat = item.getLat();
                    String lon = item.getLon();
                    String familyType = item.getFamilyType();
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", lat);
                    bundle.putString("lon", lon);
                    bundle.putString("itemId", itemId);
                    bundle.putString("familyType", familyType);
                    bundle.putString("type", "4");
                    bundle.putString("name", name);
                    bundle.putString("address", address);
                    bundle.putString("fitment", fitment);
                    bundle.putString("info", info);
                    bundle.putString("size", size);
                    bundle.putString("price", price);
                    bundle.putString("floors", floors);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, NewDetailsActivity.class);
                    startActivity(intent);
                }
            }
        });
        textView_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void  HTTP_HomePage()
    {
        OkHttpUtils.get().url(UrlConfig.Path.Search_URL+search).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_LONG).show();
                search_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        search_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString=s;
             /*   if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }*/
                totalList = parseJsonToMessageBean(jsonString);
                list = parseJsonhomeToMessageBean(jsonString);
                //homePageAdapter.reloadGridView(totalList,true);
                if (totalList.size()==0||totalList==null)
                {
                    totalList=new ArrayList<>();
                   /* linear_search.setVisibility(View.VISIBLE);
                    search_listView.setVisibility(View.INVISIBLE);*/
                    search_listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                     line_noFind.setVisibility(View.VISIBLE);
                        }
                    }, 1700);
                    linear_search.setVisibility(View.INVISIBLE);
                }else {
                    linear_search.setVisibility(View.INVISIBLE);
                    search_listView.setVisibility(View.VISIBLE);
                    line_noFind.setVisibility(View.INVISIBLE);
                }
                search_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        search_listView.onRefreshComplete();
                        homePageAdapter.reloadGridView(list,true);
                        rentingAdapter.reloadGridView(totalList,true);
                        if (totalList!=null)
                        {
                            if (totalList.size()>0)
                            {
                                search_listView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }, 1500);
            }
        });
    }
    //自定义通过gson解析获得数据数组的方法
    public List<SearchBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<SearchBean> list = gson.fromJson(jsonString, new TypeToken<List<SearchBean>>() {
        }.getType());
        return list;
    }
    //自定义通过gson解析获得数据数组的方法
    public List<HomePagerBean> parseJsonhomeToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<HomePagerBean> list = gson.fromJson(jsonString, new TypeToken<List<HomePagerBean>>() {
        }.getType());
        return list;
    }
      //点击空白处隐藏键盘的方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                editText_search.setCursorVisible(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }else {
                editText_search.setCursorVisible(true);
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
}
