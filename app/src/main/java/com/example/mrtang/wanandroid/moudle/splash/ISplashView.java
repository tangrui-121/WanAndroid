package com.example.mrtang.wanandroid.moudle.splash;


import com.example.library.base.IVBaseView;
import com.example.library.bean.LoginData;

/**
 * Created by Mr'Tang on 2018/4/25.
 */

public interface ISplashView extends IVBaseView {
    void doLoginEvent(LoginData loginData);

    void showError(String msg);
}
