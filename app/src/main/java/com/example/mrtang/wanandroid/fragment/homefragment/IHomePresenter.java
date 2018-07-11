package com.example.mrtang.wanandroid.fragment.homefragment;

import com.example.library.bean.DoclistBean;
/**
 * Created by Mr'Tang on 2018/4/24.
 */

public interface IHomePresenter {
    void collectDoc(int position, DoclistBean bean);

    void uncollectDoc(final int position, final DoclistBean bean);

    void rxjava_zip(final int mpage);
}
