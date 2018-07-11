package com.example.library.http;

import com.yanzhenjie.nohttp.rest.Response;

public interface HttpListener<T> {
    public void onSuccess(int what, Response<T> response);
    public void onFailed(int what, Response<T> response);
}
