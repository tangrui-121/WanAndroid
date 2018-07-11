package com.example.mrtang.wanandroid.moudle.login;


import com.example.library.base.IVBaseView;
import com.example.library.bean.LoginData;

/**
 * Created by Mr'Tang on 2018/5/22.
 */

public interface ILoginView extends IVBaseView {
    void showError(String msg);

    void doLoginEvent(LoginData loginData);
}
