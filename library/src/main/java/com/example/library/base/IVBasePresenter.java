package com.example.library.base;

import com.alibaba.fastjson.JSONObject;
import com.example.library.http.CallBackListener;
import com.yanzhenjie.nohttp.rest.Response;

public interface IVBasePresenter extends CallBackListener {
    @Override
    void onUpdate(int what, Response<JSONObject> data, boolean success);
}
