package com.example.mrtang.wanandroid.moudle.collect;

import com.example.library.bean.DoclistBean;
import com.example.mrtang.wanandroid.moudle.collectutils.CollectUtils;

/**
 * Created by Mr'Tang on 2018/6/5.
 */

public interface ICollectPresenter {
    void getCollectList(int page);

    void collectDoc(final int position, final DoclistBean bean);

    void uncollectDoc(final int position, final DoclistBean bean);
}
