package com.example.mrtang.wanandroid.moudle.aboutus;

import android.content.Context;
import android.content.Intent;

import com.example.library.base.BasicTitleCompatActivity;
import com.example.mrtang.wanandroid.R;

/**
 * Created by Mr'Tang on 2018/6/7.
 */

public class AboutusActivity extends BasicTitleCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, AboutusActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void updateViews(boolean isRefresh) {
        setTitle("关于");
    }
}
