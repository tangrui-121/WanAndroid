package com.example.library.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr'Tang on 2018/4/25.
 */

public class DoclistBean implements Serializable {
    private int id;
    private String author;
    private String chapterName;
    private String envelopePic;
    private String link;
    private String niceDate;
    private String desc;
    private String title;
    private Boolean collect;
    private int zan;
    private int originId;

    public static List<DoclistBean> parseFromJson(JSONArray jsonArray) {
        List<DoclistBean> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.size(); a++) {
            DoclistBean doclistBean = new DoclistBean();
            JSONObject jsonObject = jsonArray.getJSONObject(a);
            doclistBean.setId(jsonObject.getIntValue("id"));
            doclistBean.setAuthor(jsonObject.getString("author"));
            doclistBean.setEnvelopePic(jsonObject.getString("envelopePic"));
            doclistBean.setLink(jsonObject.getString("link"));
            doclistBean.setTitle(jsonObject.getString("title"));
            doclistBean.setZan(jsonObject.getIntValue("zan"));
            doclistBean.setDesc(jsonObject.getString("desc"));
            doclistBean.setCollect(jsonObject.getBoolean("collect"));
            doclistBean.setNiceDate(jsonObject.getString("niceDate"));
            doclistBean.setChapterName(jsonObject.getString("chapterName"));
            list.add(doclistBean);
        }
        return list;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
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

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
