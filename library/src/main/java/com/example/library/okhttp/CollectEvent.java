package com.example.library.okhttp;


import com.example.library.bean.DoclistBean;

/**
 * Created by Mr'Tang on 2018/6/5.
 */

public class CollectEvent {
    private int position;
    private DoclistBean doclistBean;

    public CollectEvent(int mposition, DoclistBean mdoclistBean) {
        this.position = mposition;
        this.doclistBean = mdoclistBean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DoclistBean getDoclistBean() {
        return doclistBean;
    }

    public void setDoclistBean(DoclistBean doclistBean) {
        this.doclistBean = doclistBean;
    }
}
