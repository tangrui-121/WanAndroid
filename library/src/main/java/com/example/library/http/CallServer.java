package com.example.library.http;

import android.content.Context;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

public class CallServer {

    private static CallServer instance;

    /**
     * 请求队列。
     */
    private RequestQueue requestQueue;

    private CallServer() {
        requestQueue = NoHttp.newRequestQueue(3);
    }

    /**
     * 请求队列。
     */
    public synchronized static CallServer getInstance() {
        if (instance == null)
            synchronized (CallServer.class) {
                if (instance == null)
                    instance = new CallServer();
            }
        return instance;
    }

    /**
     * 添加一个请求到请求队列。
     * @param what     用来标志请求, 当多个请求使用同一个Listener时, 在回调方法中会返回这个what。
     * @param request  请求对象。
     * @param callback 结果回调对象。
     */
    public <T> void add(int what, Request<T> request, CallBackListener callback) {
        HttpListener listener = new HttpListenerImpl(callback);
        requestQueue.add(what, (Request<T>) request, new HttpResponseListener<T>(listener,request));
    }
    /**
     * 添加一个请求到请求队列。
     * @param context  context上下文
     * @param what     用来标志请求, 当多个请求使用同一个Listener时, 在回调方法中会返回这个what。
     * @param request  请求对象。
     * @param callback 结果回调对象。
     */
    public <T> void add(Context context, int what, Request<T> request, CallBackListener callback) {
        HttpListener listener = new HttpListenerImpl(callback);
        requestQueue.add(what, (Request<T>) request, new HttpResponseListener<T>(context,listener,request));
    }
    /**
     * 取消这个sign标记的所有请求。
     *
     * @param sign 请求的取消标志。
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * 停止队列
     */
    public void stopQueue() {
        requestQueue.stop();
    }

    /**
     * 取消队列中所有请求。
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }
}
