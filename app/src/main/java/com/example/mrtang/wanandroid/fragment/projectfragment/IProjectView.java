package com.example.mrtang.wanandroid.fragment.projectfragment;

import com.example.library.base.IVBaseView;
import com.example.library.bean.NavigationChildBean;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/17.
 */

public interface IProjectView extends IVBaseView {
    void showError(String msg);
    void initData(List<NavigationChildBean> mylist);
}
