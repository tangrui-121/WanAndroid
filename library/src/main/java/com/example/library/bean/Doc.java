package com.example.library.bean;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/6/4.
 */

public class Doc {
    private List<DoclistBean> datas;

    public List<DoclistBean> getList() {
        return datas;
    }

    public void setList(List<DoclistBean> list) {
        this.datas = list;
    }
}
