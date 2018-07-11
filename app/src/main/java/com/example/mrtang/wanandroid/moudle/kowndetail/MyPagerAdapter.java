package com.example.mrtang.wanandroid.moudle.kowndetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist.KnowlistFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/15.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<KnowlistFragment> mFragmentlist;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<KnowlistFragment> fragments) {
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