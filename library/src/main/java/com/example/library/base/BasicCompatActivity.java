package com.example.library.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.example.library.R;


/**
 * Created by Mr'Tang on 2018/4/24.
 */

public abstract class BasicCompatActivity <T extends IVBasePresenter> extends AbstractCompatActivity implements AbstractFragment.OnFragmentInteractionListener{
    private FrameLayout _mContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().setContentView(R.layout.basic_activity);
        _mContentLayout = (FrameLayout) super.findViewById(R.id._content);
        setContentView(attachLayoutRes());
        initView();
        initPresenter();
        updateViews(false);
    }

    @Override
    public View findViewById(int viewId) {
        return _mContentLayout.findViewById(viewId);
    }

    @Override
    public FrameLayout getContentLayout() {
        return _mContentLayout;
    }

    @Override
    public void setContentBackground(int color) {
        _mContentLayout.setBackgroundResource(color);
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
    public void onFragmentInteraction(Uri uri) {

    }
}

