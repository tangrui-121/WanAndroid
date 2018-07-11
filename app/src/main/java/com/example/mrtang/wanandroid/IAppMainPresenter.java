package com.example.mrtang.wanandroid;


import com.example.library.base.IVBasePresenter;

public interface IAppMainPresenter extends IVBasePresenter {

    //加载fragments数据
    void initFragments();

    //切换fragment页面
    void onCheckedFragment(int position);
}
