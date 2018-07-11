package com.example.mrtang.wanandroid.moudle.login;

import android.content.Context;

import com.example.library.base.IVBasePresenter;


/**
 * Created by Mr'Tang on 2018/5/22.
 */

public interface ILoginPresenter extends IVBasePresenter {
    void login(Context context, String account, String password);
    void register(Context context, String account, String password);
}
