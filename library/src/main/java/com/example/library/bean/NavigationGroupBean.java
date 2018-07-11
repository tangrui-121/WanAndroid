package com.example.library.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/10.
 */

public class NavigationGroupBean implements Serializable {
    private String name;
    private int tag;
    private List<NavigationChildBean> list;

    public static List<NavigationGroupBean> parseFromJson(JSONArray jsonArray) {
        List<NavigationGroupBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            NavigationGroupBean navigationGroupBean = new NavigationGroupBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            navigationGroupBean.setName(jsonObject.getString("name"));
            navigationGroupBean.setList(NavigationChildBean.parseFromJson(jsonObject.getJSONArray("articles")));
            navigationGroupBean.setTag(a);
            list.add(navigationGroupBean);
        }
        return list;
    }

    public static List<NavigationGroupBean> parseFromJson1(JSONArray jsonArray) {
        List<NavigationGroupBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            NavigationGroupBean navigationGroupBean = new NavigationGroupBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            navigationGroupBean.setName(jsonObject.getString("name"));
            navigationGroupBean.setList(NavigationChildBean.parseFromJson1(jsonObject.getJSONArray("children")));
            navigationGroupBean.setTag(a);
            list.add(navigationGroupBean);
        }
        return list;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NavigationChildBean> getList() {
        return list;
    }

    public void setList(List<NavigationChildBean> list) {
        this.list = list;
    }
}
