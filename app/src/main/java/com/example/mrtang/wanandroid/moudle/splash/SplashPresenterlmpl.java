package com.example.mrtang.wanandroid.moudle.splash;

import android.content.Context;

import com.example.library.bean.LoginData;
import com.example.library.dao.DataBaseTool;
import com.example.library.http.Result;
import com.example.library.okhttp.ApiServices;
import com.example.library.okhttp.DataResponse;
import com.example.library.okhttp.RetrofitManager;
import com.example.library.okhttp.RxSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Mr'Tang on 2018/4/25.
 */

public class SplashPresenterlmpl implements ISplashPresenter {
    private ISplashView iView;

    public SplashPresenterlmpl(ISplashView view) {
        this.iView = view;
    }

    public void login(Context context, String account, String password) {
        RetrofitManager.create(ApiServices.class)
                .login(account, password)
                .compose(RxSchedulers.<DataResponse<LoginData>>applySchedulers())
                .compose(iView.<DataResponse<LoginData>>bindToLife())
                .subscribe(new Consumer<DataResponse<LoginData>>() {
                    @Override
                    public void accept(DataResponse<LoginData> response) throws Exception {
                        if (response.getErrorCode() == Result.RESULT_OK) {
                            DataBaseTool.correctUserInfo(response.getData());
                            iView.doLoginEvent(response.getData());
                        } else {
                            iView.showError(response.getErrorMsg().toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iView.showError(throwable.getMessage());
                    }
                });
    }
}
