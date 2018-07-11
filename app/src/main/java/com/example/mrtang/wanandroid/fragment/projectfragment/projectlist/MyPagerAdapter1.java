package com.example.mrtang.wanandroid.fragment.projectfragment.projectlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/6/7.
 */

public class MyPagerAdapter1 extends FragmentPagerAdapter {
    private List<ProjectlistFragment> mFragmentlist;

    public MyPagerAdapter1(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<ProjectlistFragment> fragments) {
        this.mFragmentlist = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentlist.size();
    }
}