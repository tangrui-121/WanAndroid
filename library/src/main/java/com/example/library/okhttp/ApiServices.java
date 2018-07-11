package com.example.library.okhttp;

import com.example.library.bean.BannerBean;
import com.example.library.bean.Doc;
import com.example.library.bean.LoginData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Mr'Tang on 2018/5/29.
 */

public interface ApiServices {
    /**
     * 登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<DataResponse<LoginData>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     */
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Doc>> getHomeArticles(@Path("page") int page);


    /**
     * 首页数据
     * http://www.wanandroid.com/banner/json
     */
    @GET("/banner/json")
    Observable<DataResponse<List<BannerBean>>> getBanner();

    /**
     * 收藏文章
     * http://www.wanandroid.com/lg/collect/1165/json
     */
    @POST("/lg/collect/{id}/json")
    Observable<DataResponse> collectDoc(@Path("id") int id);

    /**
     * 取消收藏
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<DataResponse> uncollectDoc(@Path("id") int id);

    /**
     * 收藏列表
     * http://www.wanandroid.com/lg/collect/list/0/json
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<DataResponse<Doc>> getCollectDoc(@Path("page") int page);

    /**
     * 取消收藏(收藏页)
     * http://www.wanandroid.com/lg/uncollect/2805/json
     * originId:列表页下发，无则为-1
     */
    @POST("/lg/collect/{id}/json")
    @FormUrlEncoded
    Observable<DataResponse> uncollectdoc(@Path("id") int id, @Field("originId") int originId);
}
