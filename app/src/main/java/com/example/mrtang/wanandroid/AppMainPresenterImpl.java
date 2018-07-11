package com.example.mrtang.wanandroid;

import android.support.v4.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.example.mrtang.wanandroid.fragment.homefragment.HomeFragment;
import com.example.mrtang.wanandroid.fragment.knowledgefragment.KownFragment;
import com.example.mrtang.wanandroid.fragment.projectfragment.ProjectFragment;
import com.example.mrtang.wanandroid.fragment.navigationfragment.Navigationfragment;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;

public class AppMainPresenterImpl implements IAppMainPresenter {
    private IAppMainView mainView;
    private boolean isForce = false;

    public AppMainPresenterImpl(IAppMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void initFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.instance(R.string.app_name));
        fragments.add(KownFragment.instance(R.string.main_title_knowledge));
        fragments.add(Navigationfragment.instance(R.string.main_title_navigation));
        fragments.add(ProjectFragment.instance(R.string.main_title_project));
        mainView.initFragment(fragments);
    }

    @Override
    public void onCheckedFragment(int position) {
        mainView.showFragment(position);
    }


    @Override
    public void onUpdate(int what, Response<JSONObject> data, boolean success) {
        switch (what) {
            default:
                break;
        }
    }
}
