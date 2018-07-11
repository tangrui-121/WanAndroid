package com.example.library.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/10.
 */

public class NavigationChildBean implements Serializable {
    private String link;
    private String title;
    private int id;
    private int zan;

    public static List<NavigationChildBean> parseFromJson(JSONArray jsonArray) {
        List<NavigationChildBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            NavigationChildBean navigationBean = new NavigationChildBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            navigationBean.setLink(jsonObject.getString("link"));
            navigationBean.setTitle(jsonObject.getString("title"));
            navigationBean.setId(jsonObject.getInteger("id"));
            navigationBean.setZan(jsonObject.getInteger("zan"));
            list.add(navigationBean);
        }
        return list;
    }

    public static List<NavigationChildBean> parseFromJson1(JSONArray jsonArray) {
        List<NavigationChildBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            NavigationChildBean navigationBean = new NavigationChildBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            navigationBean.setTitle(jsonObject.getString("name"));
            navigationBean.setId(jsonObject.getInteger("id"));
            list.add(navigationBean);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
