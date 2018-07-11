package com.example.library.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class AbstractCompatActivity<T extends IVBasePresenter> extends RxAppCompatActivity implements IVBaseView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    // -------------------- BaseActivity的辅助封装 --------------------- //

    /**
     * Presenter 注入
     */
    protected abstract void initPresenter();

    /**
     * 绑定布局文件
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initView();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);

    protected abstract FrameLayout getContentLayout();

    protected abstract void setContentBackground(int color);

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }
}
