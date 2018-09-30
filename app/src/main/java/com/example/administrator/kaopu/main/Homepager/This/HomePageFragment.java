package com.example.administrator.kaopu.main.Homepager.This;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.commond.HTTP_GD;
import com.example.administrator.kaopu.commond.UrlConfig;
import com.example.administrator.kaopu.main.Details.NewDetailsActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.AboutCopauActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.ApartmentActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.CoolPaulDecorateActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.GoldKeeperActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.HouseSellActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.InformationActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.NewHouseActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.NewsActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.OverseasActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.RentingActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.SearchActivity;
import com.example.administrator.kaopu.main.Homepager.Other.Activity.SecondHouseActivity;
import com.example.administrator.kaopu.tools.OtherImageView;
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

public class HomePageFragment extends Fragment {
    private Context mContext;
    //初始化带刷新的列表listview
    private PullToRefreshListView homePger_listView;
    private ListView listView;
    //初始化listview的适配器
    private HomePageAdapter homePageAdapter;
    //创建数据bean的数组
    private List<HomePagerBean> totalList=new ArrayList<>();
    private LinearLayout line_news;
    private LinearLayout line_newHouse;
    private LinearLayout line_secondHouse;
    private LinearLayout line_retingHouse;
    private LinearLayout line_apartment;
    private LinearLayout line_coolPaul;
    private LinearLayout line_overseas;
    private LinearLayout line_infor;
    private LinearLayout line_aboutCopau;
    private LinearLayout line_houseSell;
    private LinearLayout line_houseKeeper;
    private Handler han=new Handler();
    private LinearLayout linear_search;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        homePageAdapter=new HomePageAdapter(mContext,totalList);
        autoRefresh();
        HTTP_HomePage();

    }
    public void autoRefresh(){
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                homePger_listView.setRefreshing(true);
            }
        },200);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepager_fragment, container, false);
        homePger_listView = (PullToRefreshListView) view.findViewById(R.id.homePger_listView);
        linear_search= (LinearLayout) view.findViewById(R.id.linear_search);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //为带有刷新的listview添加头部视图
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = inflater.inflate(R.layout.homepager_head, homePger_listView, false);
        AbsListView.LayoutParams
                layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        listView = homePger_listView.getRefreshableView();
        listView.addHeaderView(headView);
        //为listview设置适配器
        homePger_listView.setAdapter(homePageAdapter);
        line_news= (LinearLayout) headView.findViewById(R.id.line_news);
        line_newHouse= (LinearLayout) headView.findViewById(R.id.line_newHouse);
        line_secondHouse= (LinearLayout) headView.findViewById(R.id.line_secondHouse);
        line_retingHouse= (LinearLayout) headView.findViewById(R.id.line_retingHouse);
        line_apartment= (LinearLayout) headView.findViewById(R.id.line_apartment);
        line_overseas= (LinearLayout) headView.findViewById(R.id.line_overseas);
        line_coolPaul=(LinearLayout)headView.findViewById(R.id.line_coolPaul);
        line_infor=(LinearLayout)headView.findViewById(R.id.line_infor);
        line_aboutCopau= (LinearLayout) headView.findViewById(R.id.line_aboutCopau);
        line_houseSell= (LinearLayout) headView.findViewById(R.id.line_houseSell);
        line_houseKeeper= (LinearLayout) headView.findViewById(R.id.line_houseKeeper);
        //设置listview带有上下拉刷新
        homePger_listView.setMode(PullToRefreshBase.Mode.BOTH);
        homePger_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                HTTP_HomePage();
                homePger_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePger_listView.onRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                homePger_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePger_listView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
        homePger_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomePagerBean item = (HomePagerBean) homePageAdapter.getItem(position-2);
                List<String> img = item.getImg();
                List<String> imgHouse = item.getImgHouse();
                String itemId = item.getId();
                String name = item.getName();
                String address = item.getAddress();
                String fitment = item.getFitment();
                String familyType = item.getFamilyType();
                String openTime = item.getOpenTime();
                String price = item.getPrice();
                String phone = item.getPhone();
                String lat = item.getLat();
                String lon = item.getLon();
                String developers = item.getDevelopers();
                Intent intent=new Intent();
                intent.putStringArrayListExtra("img", (ArrayList<String>) img);
                intent.putStringArrayListExtra("imgHouse", (ArrayList<String>) imgHouse);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", itemId);
                bundle.putString("lat", lat);
                bundle.putString("lon", lon);
                bundle.putString("type", "1");
                bundle.putString("name", name);
                bundle.putString("address", address);
                bundle.putString("fitment", fitment);
                bundle.putString("familyType", familyType);
                bundle.putString("openTime", openTime);
                bundle.putString("price", price);
                bundle.putString("developers", developers);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                intent.setClass(mContext, NewDetailsActivity.class);
                startActivity(intent);
            }
        });
        line_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, NewsActivity.class);
                startActivity(intent);
            }
        });
        //点击新房按钮跳转到新房列表页面
        line_newHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, NewHouseActivity.class);
                startActivity(intent);
            }
        });
        //点击二手房按钮跳转到二手房列表页面
        line_secondHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, SecondHouseActivity.class);
                startActivity(intent);
            }
        });
        //点击租房跳转到租房的列表页面
        line_retingHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, RentingActivity.class);
                startActivity(intent);
            }
        });
        //点击公寓跳转到公寓的列表页面
        line_apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, ApartmentActivity.class);
                startActivity(intent);
            }
        });
        //点击海外房产跳转到海外房产详情页
        line_overseas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, OverseasActivity.class);
                startActivity(intent);
            }
        });
        //点击靠谱装饰跳转到靠谱装饰详情页面
        line_coolPaul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, CoolPaulDecorateActivity.class);
                startActivity(intent);
            }
        });
        //点击资讯跳转到资讯列表页面
        line_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, InformationActivity.class);
                startActivity(intent);
            }
        });
        //点击关于靠谱跳转到靠谱详情页
        line_aboutCopau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, AboutCopauActivity.class);
                startActivity(intent);
            }
        });
        //点击热销房源跳转到列表页
        line_houseSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, HouseSellActivity.class);
                startActivity(intent);
            }
        });
        //点击金牌管家跳转到金牌管家页面
        line_houseKeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, GoldKeeperActivity.class);
                startActivity(intent);
            }
        });
        //点击搜索跳转到搜索页面
        linear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    public  void  HTTP_HomePage()
    {
        OkHttpUtils.get().url(UrlConfig.Path.HomePager_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext,"网络访问失败，请连接网络",Toast.LENGTH_LONG).show();
                homePger_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePger_listView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onResponse(String s) {
                String jsonString=s;
                if (HTTP_GD.IsOrNot_Null(jsonString))
                {
                    return;
                }
                 totalList = parseJsonToMessageBean(jsonString);
               // homePageAdapter.reloadGridView(totalList,true);
                if (totalList==null)
                {
                    totalList=new ArrayList<>();
                }
                homePger_listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePger_listView.onRefreshComplete();
                        homePageAdapter.reloadGridView(totalList,true);
                        if (totalList!=null)
                        {
                            if (totalList.size()>0)
                            {
                                homePger_listView.getRefreshableView().setSelection(0);
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
