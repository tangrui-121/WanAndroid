package com.example.mrtang.wanandroid.moudle.webview;

import com.example.library.bean.DoclistBean;

/**
 * Created by Mr'Tang on 2018/6/5.
 */

public interface ICommonWebviewPresenter {
    void collectDoc(final int position, final DoclistBean bean);

    void uncollectDoc(final int position, final DoclistBean bean);

    void uncollectdoc(final int position, final DoclistBean bean);
}
