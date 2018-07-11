package com.example.library.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/4/17.
 */

public class BannerBean implements Serializable {
    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    public static List<BannerBean> parseFromJson(JSONArray jsonArray) {
        List<BannerBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            BannerBean bannerBean = new BannerBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            bannerBean.setDesc(jsonObject.getString("desc"));
            bannerBean.setId(jsonObject.getIntValue("id"));
            bannerBean.setImagePath(jsonObject.getString("imagePath"));
            bannerBean.setIsVisible(jsonObject.getIntValue("isVisible"));
            bannerBean.setOrder(jsonObject.getIntValue("order"));
            bannerBean.setTitle(jsonObject.getString("title"));
            bannerBean.setType(jsonObject.getIntValue("type"));
            bannerBean.setUrl(jsonObject.getString("url"));
            list.add(bannerBean);
        }
        return list;
    }

    public static List<BannerBean> parseFromJson1(JSONArray jsonArray) {
        List<BannerBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            BannerBean bannerBean = new BannerBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            bannerBean.setImagePath(jsonObject.getString("url"));
            bannerBean.setTitle(jsonObject.getString("type"));
            list.add(bannerBean);
        }
        return list;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

