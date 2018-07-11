package com.example.library.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.library.R;


public abstract class BasicTitleCompatActivity<T extends IVBasePresenter> extends AbstractCompatActivity {
    private AppBarLayout _mAppBarLayout;
    private Toolbar _mToolbar;
    private FrameLayout _mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().setContentView(R.layout.basic_activity_title);

        _mAppBarLayout = (AppBarLayout) super.findViewById(R.id._appbarlayout);
        _mToolbar = (Toolbar) super.findViewById(R.id._toolbar);
        _mContentLayout = (FrameLayout) super.findViewById(R.id._content);
        _mToolbar.setTitle("");
        setSupportActionBar(_mToolbar);
        setBackBar(true);
        setContentView(attachLayoutRes());
        initView();
        initPresenter();
        updateViews(false);
    }

    @Override
    public View findViewById(int viewId) {
        return _mContentLayout.findViewById(viewId);
    }

    public FrameLayout getContentLayout() {
        return _mContentLayout;
    }

    @Override
    protected void setContentBackground(int color) {

    }

    public AppBarLayout getAppBarLayout() {
        return _mAppBarLayout;
    }

    public Toolbar getmToolbar() {
        return _mToolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
        _mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        _mToolbar.setTitle(titleId);
    }

    public void setSubtitle(CharSequence title) {
        _mToolbar.setSubtitle(title);
    }

    public void setSubtitle(int titleId) {
        _mToolbar.setSubtitle(titleId);
    }

    public void setSubtitleTextColor(int color) {
        _mToolbar.setSubtitleTextColor(color);
    }

    public void setTitleTextColor(int color) {
        _mToolbar.setTitleTextColor(color);
    }

    public void setBackBar(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);

    }

    @Override
    public void setContentView(int layoutResID) {
        _mContentLayout.removeAllViews();
        getLayoutInflater().inflate(layoutResID, _mContentLayout, true);
    }

    @Override
    public void setContentView(View view) {
        _mContentLayout.removeAllViews();
        _mContentLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        _mContentLayout.removeAllViews();
        _mContentLayout.addView(view, params);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return onOptionsItemSelectedCompat(item);
    }

    protected boolean onOptionsItemSelectedCompat(MenuItem item) {
        return false;
    }
}
