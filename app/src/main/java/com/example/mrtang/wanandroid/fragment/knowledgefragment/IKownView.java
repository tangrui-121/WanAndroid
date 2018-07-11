package com.example.mrtang.wanandroid.fragment.knowledgefragment;

import com.example.library.base.IVBaseView;
import com.example.library.bean.NavigationGroupBean;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/15.
 */

public interface IKownView extends IVBaseView {
    void showError(String msg);

    void initData(List<NavigationGroupBean> mylist);
}
