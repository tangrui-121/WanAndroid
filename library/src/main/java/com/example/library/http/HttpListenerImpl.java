package com.example.library.http;

import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.nohttp.rest.Response;

public class HttpListenerImpl implements HttpListener<JSONObject> {
    private CallBackListener callback;

    public HttpListenerImpl(CallBackListener callback) {
        this.callback = callback;
    }

    @Override
    public void onSuccess(int what, Response<JSONObject> response) {
        callback.onUpdate(what,response,true);
    }

    @Override
    public void onFailed(int what, Response<JSONObject> response) {
        callback.onUpdate(what,response,false);
    }
}
