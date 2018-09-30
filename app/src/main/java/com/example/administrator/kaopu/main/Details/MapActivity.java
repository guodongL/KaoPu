package com.example.administrator.kaopu.main.Details;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.kaopu.R;
import com.githang.statusbar.StatusBarCompat;

public class MapActivity extends AppCompatActivity {
private MapView bmapView;
    private BaiduMap mBaiduMap;
    private ImageView image_back;
    private String lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initSet();
    }

    private void initView() {
        Intent intent=this.getIntent();
         lat = intent.getStringExtra("lat");
         lon = intent.getStringExtra("lon");
        bmapView = (MapView) findViewById(R.id.bmapView);
        image_back= (ImageView) findViewById(R.id.image_back);
    }
    private void initSet() {
        mBaiduMap = bmapView.getMap(); //获得地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置为普通模式的地图
        addMarkerOption(mBaiduMap);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //添加标记覆盖物
    private void addMarkerOption(BaiduMap baiduMap) {
        double latD = Double.parseDouble(lat);
        double lonD = Double.parseDouble(lon);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_map);
        LatLng position = new LatLng(latD,lonD);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        bmapView.onDestroy();
        bmapView = null;
    }
}
