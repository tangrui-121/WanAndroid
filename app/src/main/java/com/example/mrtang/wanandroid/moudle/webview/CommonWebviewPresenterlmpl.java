package com.example.mrtang.wanandroid.moudle.webview;

import com.example.library.bean.DoclistBean;
import com.example.mrtang.wanandroid.moudle.collectutils.CollectUtils;

/**
 * Created by Mr'Tang on 2018/6/5.
 */

public class CommonWebviewPresenterlmpl implements ICommonWebviewPresenter {
    private ICommonWebviewView iView;

    public CommonWebviewPresenterlmpl(ICommonWebviewView iview) {
        this.iView = iview;
    }

    public void collectDoc(final int position, final DoclistBean bean) {
        CollectUtils.collectDoc(iView, position, bean);
    }

    public void uncollectDoc(final int position, final DoclistBean bean) {
        CollectUtils.uncollectDoc(iView, position, bean);
    }

    @Override
    public void uncollectdoc(int position, DoclistBean bean) {
        CollectUtils.uncollectoc(iView, position, bean);
    }
}
