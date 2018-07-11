package com.example.mrtang.wanandroid.moudle.login;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.example.library.application.Consts;
import com.example.library.bean.LoginData;
import com.example.library.dao.DataBaseTool;
import com.example.library.http.CallServer;
import com.example.library.http.RequestUtils;
import com.example.library.http.Result;
import com.example.library.okhttp.ApiServices;
import com.example.library.okhttp.DataResponse;
import com.example.library.okhttp.RetrofitManager;
import com.example.library.okhttp.RxSchedulers;
import com.example.mrtang.wanandroid.R;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import io.reactivex.functions.Consumer;

/**
 * Created by Mr'Tang on 2018/5/22.
 */

public class LoginPresenterlmpl implements ILoginPresenter {
    private ILoginView iView;
    private Request request;

    private Context context;
    private String account, password;

    public LoginPresenterlmpl(ILoginView view) {
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
                            DataBaseTool.insertUserInfo(response.getData());
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

    public void register(Context context, String account, String password) {
        this.context = context;
        this.account = account;
        this.password = password;
        request = RequestUtils.register(Consts.SERVER_IP_register, account, password);
        if (context == null) {
            CallServer.getInstance().add(2, request, this);
        } else {
            CallServer.getInstance().add(context, 2, request, this);
        }
    }


    @Override
    public void onUpdate(int what, Response<JSONObject> data, boolean success) {
        switch (what) {
            case 2:
                if (success) {
                    if (data.get().getIntValue("errorCode") == Result.RESULT_OK) {
                        iView.showError(context.getString(R.string.register_success));
                        login(context, account, password);
                    } else {
                        iView.showError(data.get().getString("errorMsg"));
                    }
                }
                break;
            default:
                break;
        }
    }
}

