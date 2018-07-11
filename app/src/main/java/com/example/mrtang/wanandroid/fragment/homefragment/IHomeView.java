package com.example.mrtang.wanandroid.fragment.homefragment;

import com.example.library.base.IVBaseView;
import com.example.library.bean.BannerBean;
import com.example.library.bean.DoclistBean;

import java.util.List;

/**
 * Created by Mr'Tang on 2018/4/24.
 */

public interface IHomeView extends IVBaseView {
    void initBanner(List<BannerBean> bannerBeans);

    void initData(List<DoclistBean> doclistBeans, boolean is_firstpage);

    void collectArticleSuccess(int position, DoclistBean bean);
}
