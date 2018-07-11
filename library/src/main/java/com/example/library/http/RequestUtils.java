package com.example.library.http;

import com.yanzhenjie.nohttp.RequestMethod;

public class RequestUtils {
    public static RequestParams login(String url, String account, String password) {
        RequestParams params = new RequestParams(url, RequestMethod.POST, false);
        params.add("username", account);
        params.add("password", password);
        return params;
    }

    public static RequestParams register(String url, String account, String password) {
        RequestParams params = new RequestParams(url, RequestMethod.POST, false);
        params.add("username", account);
        params.add("password", password);
        params.add("repassword", password);
        return params;
    }

    public static RequestParams getInfo(String url) {
        RequestParams params = new RequestParams(url, RequestMethod.GET, false);
        return params;
    }
}
