package com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist;

import com.example.library.base.IVBaseView;
import com.example.library.bean.DoclistBean;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/5/16.
 */

public interface IKnowlistView extends IVBaseView {
    void showError(String msg);

    void initData(List<DoclistBean> doclistBeans, boolean is_firstpage);

    void collectArticleSuccess(int position, DoclistBean bean);
}
