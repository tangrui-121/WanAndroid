package com.example.mrtang.wanandroid;

import android.support.v4.app.Fragment;


import com.example.library.base.IVBaseView;

import java.util.List;

public interface IAppMainView extends IVBaseView {

    /*初始化fragments*/
    void initFragment(List<Fragment> fragments);

    /**
     * 显示默认fragment
     */
    void setDefaultFragment(int position);

    /**
     * 显示指定fragment
     */
    void showFragment(int position);

    /**
     * 更新消息图片未读状态
     */
    void updateMessNum(int num);

    /**
     * toast
     */
    void showExitTips();
}
