package com.example.administrator.kaopu.main.Recommend.This;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class RecommendPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list = null;
    private String[] arrRedTitles = null;

    public RecommendPagerAdapter(FragmentManager fm, List<Fragment> list, String[] arrRedTitles) {
        super(fm);
        this.list = list;
        this.arrRedTitles = arrRedTitles;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrRedTitles[position];
    }
}
