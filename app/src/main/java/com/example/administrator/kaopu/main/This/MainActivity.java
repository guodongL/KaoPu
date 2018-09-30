package com.example.administrator.kaopu.main.This;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.main.Homepager.This.HomePageFragment;
import com.example.administrator.kaopu.main.Information.This.InformatioFragment;
import com.example.administrator.kaopu.main.Recommend.This.RecommendFragment;
import com.example.administrator.kaopu.main.Shop.This.StoreFragment;
import com.example.administrator.kaopu.tools.FragmentHelper;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup_main;
    private RadioButton[] arrRadioButton;
    private Drawable[] arrIconDrawable;
    private Context mContext=this;
    private String[] arrBaseTitles;
    private FragmentManager manager;
    private List<Fragment> totalList=new ArrayList<>();
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        initView();
        initData();
        initTabs();
    }
    //初始化控件
    private void initView() {
        radioGroup_main= (RadioGroup) findViewById(R.id.radioGroup_main);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.commit();
    }
    private void initData() {
        arrBaseTitles=getResources().getStringArray(R.array.arrbaseTitles);
        TypedArray typedArray=getResources().obtainTypedArray(R.array.arrIconDrawable);
        arrIconDrawable=new Drawable[arrBaseTitles.length];
        for (int i=0;i<arrBaseTitles.length;i++){
            arrIconDrawable[i]=typedArray.getDrawable(i);
        }
        //初始化碎片，添加碎片
        arrRadioButton=new RadioButton[arrBaseTitles.length];
        //初始化首页碎片
        HomePageFragment homePageFragment=new HomePageFragment();
        InformatioFragment informatioFragment=new InformatioFragment();
        RecommendFragment recommendFragment=new RecommendFragment();
        StoreFragment storeFragment =new StoreFragment();
        totalList.add(homePageFragment);
        totalList.add(informatioFragment);
        totalList.add(recommendFragment);
        totalList.add(storeFragment);
        FragmentHelper.replaceFragment(manager,totalList,0,R.id.fragmentLayout_main,0,0);

    }
    private void initTabs() {
        for (int i=0;i<arrBaseTitles.length;i++){
            arrRadioButton[i]=new RadioButton(mContext);
            arrRadioButton[i].setText(arrBaseTitles[i]);
            arrRadioButton[i].setTextColor(Color.GRAY);
            arrRadioButton[i].setTextSize(12);
            arrRadioButton[i].setButtonDrawable(android.R.color.transparent);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth=dm.widthPixels;
            arrRadioButton[i].setWidth(screenWidth/4);
            arrRadioButton[i].setGravity(Gravity.CENTER);
            arrIconDrawable[i].setBounds(0,0,35,35);
            arrRadioButton[i].setCompoundDrawables(null,arrIconDrawable[i],null,null);
            arrRadioButton[i].setCompoundDrawablePadding(6);
            arrIconDrawable[i].setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            radioGroup_main.addView(arrRadioButton[i]);
        }
        final int tabColor=getResources().getColor(R.color.red);
        arrRadioButton[0].setTextColor(tabColor);
        arrIconDrawable[0].setColorFilter(tabColor, PorterDuff.Mode.SRC_IN);
        radioGroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0;i<arrBaseTitles.length;i++){
                    arrRadioButton[i].setTextColor(Color.GRAY);
                    arrIconDrawable[i].setColorFilter(Color.GRAY,PorterDuff.Mode.SRC_IN);
                    if (arrRadioButton[i].getId()==checkedId){
                        arrRadioButton[i].setTextColor(tabColor);
                        arrIconDrawable[i].setColorFilter(tabColor,PorterDuff.Mode.SRC_IN);
                        FragmentHelper.switchFragment(manager,totalList,i,R.id.fragmentLayout_main,0,0);
                    }
                }
            }
        });
    }

}
