package com.example.library.http;

import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.nohttp.rest.Response;

public interface CallBackListener {
    public void onUpdate(int what, Response<JSONObject> data, boolean success);
}
