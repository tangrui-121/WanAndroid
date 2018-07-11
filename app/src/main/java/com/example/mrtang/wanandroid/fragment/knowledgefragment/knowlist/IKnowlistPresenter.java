package com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist;

import android.content.Context;

import com.example.library.base.IVBasePresenter;
import com.example.library.bean.DoclistBean;
import com.example.mrtang.wanandroid.moudle.collectutils.CollectUtils;

/**
 * Created by Mr'Tang on 2018/5/16.
 */

public interface IKnowlistPresenter extends IVBasePresenter {
    void getKnowlistData(Context context, int id, int page);

    void collectDoc(final int position, final DoclistBean bean);

    void uncollectDoc(final int position, final DoclistBean bean);
}
