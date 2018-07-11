package com.example.library.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.utils.DisplayUtils;
import com.example.library.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractFragment<T extends IVBasePresenter> extends RxFragment implements IVBaseView {
    private OnFragmentInteractionListener _mFragmentListener;
    protected View _mView;
    protected int headViewSize;
    protected Context mContext;
    protected FragmentActivity _factivity;
    protected boolean mIsMulti;
    private Unbinder unbinder;
    protected static final String TITLE_KEY = "title_key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DisplayUtils.initScreen(getActivity());
        headViewSize = DisplayUtils.dip2px(42);
        if (_mView == null) {
            _mView = inflater.inflate(attachLayoutRes(), null);
            initViews();
            initPresenter();
        }
        ViewGroup parent = (ViewGroup) _mView.getParent();
        if (parent != null) {
            parent.removeView(_mView);
        }
        unbinder = ButterKnife.bind(this, _mView);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && _mView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);
        }
    }

    protected View findViewById(int id) {
        View v = _mView.findViewById(id);
        if (v != null)
            return v;
        else
            throw new NullPointerException(this.getClass().getSimpleName()
                    + " View is null,could not find resouce id");
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * Dagger 注入
     */
    protected abstract void initPresenter();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    protected abstract void updateViews(boolean isRefresh);

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (_mFragmentListener != null) {
            _mFragmentListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            _mFragmentListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mFragmentListener = null;
    }

    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, @StringRes int title) {
        toolbar.setTitle(title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && _mView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {//这段代码非常重要，缺少会导致fragment重叠
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}
