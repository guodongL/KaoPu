package com.example.administrator.kaopu.main.Details;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.administrator.kaopu.R;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ryane.banner_lib.AdPageInfo;
import com.ryane.banner_lib.AdPlayBanner;

import java.util.ArrayList;
import java.util.List;

import static com.ryane.banner_lib.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner_lib.AdPlayBanner.IndicatorType.POINT_INDICATOR;

public class NewDetailsActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private Context mContext = this;
    private AdPlayBanner mAdPlayBanner;
    private List<NewDetailsBean> list = new ArrayList<>();
    private String itemId, name, familyType, price, address, openTime, developers, fitment, type, phone,lat,lon;
    private ImageView image_back;
    private ImageView image_houseMap;
    private ImageView image_nextMap;
    private TextView text_name;
    private TextView text_familyType;
    private TextView text_price;
    private TextView text_address;
    private TextView text_openTime;
    private TextView text_developers;
    private TextView text_fitment;
    private ImageView text_houseMap;
    private TextView text_picNumber;
    private RelativeLayout relative_housemap;
    private RelativeLayout relative_nexmap;
    private TextView text_priceTitle;
    private TextView text_adressTitle;
    private TextView text_timeTitle;
    private TextView text_developersTitle;
    private TextView text_family;
    private TextView text_fourSale, text_threeSale, text_twoSale, text_oneSale, text_otherDetail;
    private MapView mapView_details;
    private BaiduMap mBaiduMap;
    private LinearLayout line_map;
    private LinearLayout line_phone;
    private RelativeLayout relative_newHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initClick();
        //HTTP_Details();
    }

    private void initView() {
        relative_newHouse= (RelativeLayout) findViewById(R.id.relative_newHouse);
        line_phone = (LinearLayout) findViewById(R.id.line_phone);
        line_map = (LinearLayout) findViewById(R.id.line_map);
        mapView_details = (MapView) findViewById(R.id.mapView_details);
        text_fourSale = (TextView) findViewById(R.id.text_foourSale);
        text_threeSale = (TextView) findViewById(R.id.text_threeSale);
        text_twoSale = (TextView) findViewById(R.id.text_twoSale);
        text_oneSale = (TextView) findViewById(R.id.text_oneSale);
        text_otherDetail = (TextView) findViewById(R.id.text_otherDetail);
        text_family = (TextView) findViewById(R.id.text_family);
        text_priceTitle = (TextView) findViewById(R.id.text_priceTitle);
        text_adressTitle = (TextView) findViewById(R.id.text_adressTitle);
        text_timeTitle = (TextView) findViewById(R.id.text_timeTitle);
        text_developersTitle = (TextView) findViewById(R.id.text_developersTitle);
        relative_housemap = (RelativeLayout) findViewById(R.id.relative_housemap);
        relative_nexmap = (RelativeLayout) findViewById(R.id.relative_nexmap);
        text_picNumber = (TextView) findViewById(R.id.text_picNumber);
        text_houseMap = (ImageView) findViewById(R.id.text_houseMap);
        image_houseMap = (ImageView) findViewById(R.id.image_houseMap);
        image_nextMap = (ImageView) findViewById(R.id.image_nextMap);
        text_name = (TextView) findViewById(R.id.text_name);
        text_fitment = (TextView) findViewById(R.id.text_fitment);
        text_price = (TextView) findViewById(R.id.text_price);
        text_familyType = (TextView) findViewById(R.id.text_familyType);
        text_address = (TextView) findViewById(R.id.text_address);
        text_openTime = (TextView) findViewById(R.id.text_openTime);
        text_developers = (TextView) findViewById(R.id.text_developers);
        image_back = (ImageView) findViewById(R.id.image_back);
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        type = extras.getString("type");
        if (type.equals("1")) {
            itemId = extras.getString("itemId");
            price = extras.getString("price");
            familyType = extras.getString("familyType");
            address = extras.getString("address");
            openTime = extras.getString("openTime");
            developers = extras.getString("developers");
            text_address.setText(address);
            text_developers.setText(developers);
            text_familyType.setText(familyType);
            text_fitment.setText(fitment);
            text_openTime.setText(openTime);
            text_price.setText(price);
            List<String> imgHouse = (List<String>) extras.get("imgHouse");
            if (imgHouse.size() == 0 || imgHouse == null) {
                relative_newHouse.setVisibility(View.VISIBLE);
                text_houseMap.setVisibility(View.VISIBLE);
                relative_nexmap.setVisibility(View.INVISIBLE);
                relative_housemap.setVisibility(View.INVISIBLE);
            } else {
                Glide.with(mContext).load(imgHouse.get(0)).into(image_houseMap);
                Glide.with(mContext).load(imgHouse.get(1)).into(image_nextMap);
            }
        } else if (type.equals("2")) {
            text_houseMap.setVisibility(View.GONE);
            relative_newHouse.setVisibility(View.GONE);
            text_otherDetail.setVisibility(View.VISIBLE);
            text_familyType.setVisibility(View.INVISIBLE);
            text_adressTitle.setText("楼盘面积");
            text_timeTitle.setText("楼盘地址");
            text_developersTitle.setText("楼房层数");
            text_family.setText("房源信息");
            String size = extras.getString("size");
            String floors = extras.getString("floors");
            String info = extras.getString("info");
            address = extras.getString("address");
            price = extras.getString("price");
            name = extras.getString("name");
            text_price.setText(price);
            text_openTime.setText(address);
            text_address.setText(size);
            text_developers.setText(floors);
            text_otherDetail.setText(info);
            text_oneSale.setText("学区房");
            text_oneSale.setBackgroundColor(Color.parseColor("#ecf8d8"));
            text_twoSale.setText("南北通透");
            text_twoSale.setBackgroundColor(Color.parseColor("#eef0fe"));
            text_threeSale.setText("交通便利");
            text_threeSale.setBackgroundColor(Color.parseColor("#fff0df"));
            text_fourSale.setText("品质小区");
            text_fourSale.setBackgroundColor(Color.parseColor("#ffdae1"));
            relative_housemap.setVisibility(View.INVISIBLE);
            relative_nexmap.setVisibility(View.INVISIBLE);
        } else if (type.equals("3")) {
            text_houseMap.setVisibility(View.GONE);
            relative_newHouse.setVisibility(View.GONE);
            text_adressTitle.setText("楼盘面积");
            text_timeTitle.setText("楼盘地址");
            text_developersTitle.setText("楼房层数");
            text_family.setText("房源信息");
            relative_housemap.setVisibility(View.INVISIBLE);
            relative_nexmap.setVisibility(View.INVISIBLE);
            text_otherDetail.setVisibility(View.VISIBLE);
            String payment = extras.getString("payment");
            text_familyType.setText(payment);
            String size = extras.getString("size");
            String floors = extras.getString("floors");
            String info = extras.getString("info");
            address = extras.getString("address");
            price = extras.getString("price");
            text_price.setText(price);
            text_openTime.setText(address);
            text_address.setText(size);
            text_developers.setText(floors);
            text_otherDetail.setText(info);
            text_oneSale.setText("南北通透");
            text_oneSale.setBackgroundColor(Color.parseColor("#eef0fe"));
            text_twoSale.setText("交通便利");
            text_twoSale.setBackgroundColor(Color.parseColor("#fff0df"));
            text_threeSale.setText("家具齐全");
            text_threeSale.setBackgroundColor(Color.parseColor("#ffd9dc"));
            text_fourSale.setText("提供供暖");
            text_fourSale.setBackgroundColor(Color.parseColor("#ffd2c9"));
        } else if (type.equals("4")) {
            text_houseMap.setVisibility(View.GONE);
            relative_newHouse.setVisibility(View.GONE);
            text_otherDetail.setVisibility(View.VISIBLE);
            text_familyType.setVisibility(View.INVISIBLE);
            relative_housemap.setVisibility(View.INVISIBLE);
            relative_nexmap.setVisibility(View.INVISIBLE);
            text_adressTitle.setText("楼盘地址");
            text_timeTitle.setText("楼盘面积");
            text_developersTitle.setText("楼房层数");
            text_family.setText("房源信息");
            address = extras.getString("address");
            price = extras.getString("price");
            familyType = extras.getString("familyType");
            String size = extras.getString("size");
            String floors = extras.getString("floors");
            String info = extras.getString("info");
            text_openTime.setText(size + "(" + familyType + ")");
            text_address.setText(address);
            text_developers.setText(floors);
            text_otherDetail.setText(info);
            text_price.setText(price);
            text_oneSale.setText("手续齐全");
            text_oneSale.setBackgroundColor(Color.parseColor("#f5e0f9"));
            text_twoSale.setText("在租");
            text_twoSale.setBackgroundColor(Color.parseColor("#d9e0f9"));
            text_threeSale.setText("不挡光");
            text_threeSale.setBackgroundColor(Color.parseColor("#ffe3f1"));
            text_fourSale.setText("提供供暖");
            text_fourSale.setVisibility(View.INVISIBLE);
        }
        lat = extras.getString("lat");
        lon = extras.getString("lon");
        phone = extras.getString("phone");
        fitment = extras.getString("fitment");
        name = extras.getString("name");
        text_name.setText(name);
        text_fitment.setText(fitment);
        List<String> img = (List<String>) extras.get("img");
        mAdPlayBanner = (AdPlayBanner) findViewById(R.id.game_banner);
        List<AdPageInfo> mDatas = new ArrayList<>();
        for (int i = 0; i < img.size(); i++) {
            AdPageInfo info1 = new AdPageInfo("", img.get(i), "", i + 1);
            mDatas.add(info1);
        }
        // 设置Glide类型的图片加载方式,轮播时间间隔
        mAdPlayBanner.setInfoList((ArrayList<AdPageInfo>) mDatas).setImageLoadType(GLIDE).setIndicatorType(POINT_INDICATOR).setInterval(2000)
                .setUp();

        text_picNumber.setText("共" + img.size() + "" + "张");
    }

    private void initClick() {
        mBaiduMap = mapView_details.getMap(); //获得地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置为普通模式的地图
        addMarkerOption(mBaiduMap);
        line_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((phone != null) && (!"".equals(phone.trim()))) {
                    if (ContextCompat.checkSelfPermission(mContext,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        // 没有获得授权，申请授权
                        if (ActivityCompat.shouldShowRequestPermissionRationale(NewDetailsActivity.this,
                                Manifest.permission.CALL_PHONE)) {
                            // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                            // 弹窗需要解释为何需要该权限，再次请求授权
                            Toast.makeText(mContext, "请授权！", Toast.LENGTH_LONG).show();

                            // 帮跳转到该应用的设置界面，让用户手动授权
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }else{
                            // 不需要解释为何需要该权限，直接请求授权
                            ActivityCompat.requestPermissions(NewDetailsActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                    }else {
                        // 已经获得授权，可以打电话
                        CallPhone();
                    }
                }
            }
        });
        line_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                intent.setClass(mContext,MapActivity.class);
                startActivity(intent);
            }
        });
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void CallPhone() {
        // 拨号：激活系统的拨号组件
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        Uri data = Uri.parse("tel:" + phone); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }
    //自定义通过gson解析获得数据数组的方法
    public List<NewDetailsBean> parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        List<NewDetailsBean> list = gson.fromJson(jsonString, new TypeToken<List<NewDetailsBean>>() {
        }.getType());
        return list;
    }
    //添加标记覆盖物
    private void addMarkerOption(BaiduMap baiduMap) {
        double latD = Double.parseDouble(lat);
        double lonD = Double.parseDouble(lon);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_map);
        LatLng position = new LatLng(latD, lonD);
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(position);
        baiduMap.setMapStatus(status);//直接到中间
        MarkerOptions marker = new MarkerOptions().icon(icon).position(position).rotate(45).zIndex(9).draggable(true);
        baiduMap.addOverlay(marker);
        MapStatus.Builder builder = new MapStatus.Builder();
        // 缩放的等级 15.0f
        builder.zoom(15.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        baiduMap.setMapStatus(status);
    }
    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdPlayBanner.stop();
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mapView_details.onDestroy();
            mapView_details = null;
        }


}
