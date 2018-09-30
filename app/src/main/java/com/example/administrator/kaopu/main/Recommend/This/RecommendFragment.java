package com.example.administrator.kaopu.main.Recommend.This;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.main.Recommend.Other.ApartmentFragment;
import com.example.administrator.kaopu.main.Recommend.Other.NewHouseFragment;
import com.example.administrator.kaopu.main.Recommend.Other.RentingFragment;
import com.example.administrator.kaopu.main.Recommend.Other.SecondHouseFragment;
import com.example.administrator.kaopu.tools.TabLayoutUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class RecommendFragment extends Fragment{
    private String[] arrRedTitles = null;
    private List<Fragment> list=new ArrayList<>();
    private TabLayout trading_tablayout;
    private ViewPager trading_viewPager;
    private RecommendPagerAdapter adapter;
    private Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_fragment, container, false);
        trading_tablayout=(TabLayout)view.findViewById(R.id.trading_tablayout);
        trading_viewPager= (ViewPager)view.findViewById(R.id.trading_viewPager);
        trading_tablayout.setTabMode(TabLayout.MODE_FIXED);
        trading_tablayout.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutUtils.setIndicator(trading_tablayout,25,25);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        trading_viewPager.setOffscreenPageLimit(3);
        arrRedTitles=getResources().getStringArray(R.array.arrRedTitles);
        NewHouseFragment newFragment=new NewHouseFragment();
        SecondHouseFragment secondFragment=new SecondHouseFragment();
        RentingFragment rentingFragment=new RentingFragment();
        ApartmentFragment apartmentFragment=new ApartmentFragment();
        list.add(newFragment);
        list.add(secondFragment);
        list.add(rentingFragment);
        list.add(apartmentFragment);
        adapter=new RecommendPagerAdapter(getFragmentManager(),list,arrRedTitles);
        trading_viewPager.setAdapter(adapter);
        trading_tablayout.setupWithViewPager(trading_viewPager);
    }
}
