package com.example.administrator.kaopu.commond;

import android.app.Application;
import android.content.SharedPreferences;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;


/**
 * Created by Administrator on 2017/9/4.
 */

public class MyApp extends Application {
    private static SharedPreferences sp;
    public double latitude;
    public double longitude;
    public LocationClient mClient;
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);

        sp = getSharedPreferences("config", MODE_PRIVATE);

        //①.创建定位客户端
        mClient = new LocationClient(this);

        //②.添加定位监听
        mClient.registerLocationListener(new MyLocationListener());

        //③.设置定位客户端的参数
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 1000;
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mClient.setLocOption(option);

        //开始定位
        mClient.start();
        //mClient.stop();
    }

    //实现百度的定位监听
    class MyLocationListener implements BDLocationListener {
        //接受定位结果
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //String buildingName = bdLocation.getBuildingName();
            //String city = bdLocation.getCity();
            //String cityCode = bdLocation.getCityCode();
            //String country = bdLocation.getCountry();
            //String floor = bdLocation.getFloor();
            //纬度
            latitude = bdLocation.getLatitude();
            //经度
            longitude = bdLocation.getLongitude();

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("latitude", String.valueOf(latitude));
            editor.putString("longitude", String.valueOf(longitude));
            editor.apply();

            //String province = bdLocation.getProvince();
            //String street = bdLocation.getStreet();
            //            Log.i("TAG", "buildName=" + buildingName);
            //            Log.i("TAG", "city=" + city + ",cityCode=" + cityCode);
            //            Log.i("TAG", "country=" + country);
            //            Log.i("TAG", "floor=" + floor);
            //            Log.i("TAG", "latitude=" + latitude + ",longitude=" + longitude);
            //            Log.i("TAG", "province=" + province);
            //            Log.i("TAG", "street=" + street);
        }
    }
}
