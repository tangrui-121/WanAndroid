package com.example.mrtang.wanandroid.moudle.splash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.dl7.tag.TagView;
import com.example.library.base.BasicCompatActivity;
import com.example.library.base.IVBasePresenter;
import com.example.library.bean.LoginData;
import com.example.library.dao.DataBaseTool;
import com.example.mrtang.wanandroid.AppMainActivity;
import com.example.mrtang.wanandroid.R;
import com.example.mrtang.wanandroid.moudle.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mr'Tang on 2018/4/25.
 */

public class SplashActivity extends BasicCompatActivity<IVBasePresenter> implements ISplashView {
    private TagView mTagView;
    private CountDownTimer timer;
    private LoginData loginData;
    private ISplashPresenter mPresenter;

    @Override
    protected void initPresenter() {
        mPresenter = new SplashPresenterlmpl(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        mTagView = (TagView) findViewById(R.id.tag_skip);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        loginData = DataBaseTool.SearchUserInfo();
        mTagView.setTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int i, String s, int i1) {
                if (timer != null) {
                    timer.cancel();
                    doStips();
                }
            }
        });
        startStipsView();

    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }

        super.onDestroy();
    }

    public void startStipsView() {
        timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTagView.setText("跳过 " + (int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mTagView.setText("跳过 0");
                doStips();
            }
        }.start();
    }

    public void doStips() {
        if (loginData == null) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            mPresenter.login(this, loginData.getUsername(), loginData.getPassword());
        }
    }

    @Override
    public void doLoginEvent(LoginData loginData) {
        if (loginData == null) {
            showError(getString(R.string.auto_login_fail));
            return;
        }
        showError(getString(R.string.auto_login_success));
        Intent intent = new Intent(this, AppMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
