package com.example.girl.module.grilhome;

import com.example.girl.R;
import com.example.library.base.BasicTitleCompatActivity;

/**
 * Created by Mr'Tang on 2018/7/3.
 */

public class GrilHomeActivity extends BasicTitleCompatActivity {
    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_girlhome;
    }

    @Override
    protected void initView() {
        setTitle("Paint");
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
