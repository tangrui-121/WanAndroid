package com.example.mrtang.wanandroid.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments == null && fragments.size()<1) {
            throw new IllegalArgumentException("Fragments is null or size <1");
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
