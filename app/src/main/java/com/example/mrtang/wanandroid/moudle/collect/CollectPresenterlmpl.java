package com.example.mrtang.wanandroid.moudle.collect;

import com.example.library.bean.Doc;
import com.example.library.bean.DoclistBean;
import com.example.library.http.Result;
import com.example.library.okhttp.ApiServices;
import com.example.library.okhttp.DataResponse;
import com.example.library.okhttp.RetrofitManager;
import com.example.library.okhttp.RxSchedulers;
import com.example.mrtang.wanandroid.moudle.collectutils.CollectUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Mr'Tang on 2018/6/5.
 */

public class CollectPresenterlmpl implements ICollectPresenter {
    private ICollectView iView;

    public CollectPresenterlmpl(ICollectView iView) {
        this.iView = iView;
    }

    public void getCollectList(final int page) {
        RetrofitManager.create(ApiServices.class)
                .getCollectDoc(page)
                .compose(RxSchedulers.<DataResponse<Doc>>applySchedulers())
                .compose(iView.<DataResponse<Doc>>bindToLife())
                .subscribe(new Consumer<DataResponse<Doc>>() {
                    @Override
                    public void accept(DataResponse<Doc> response) throws Exception {
                        if (response.getErrorCode() == Result.RESULT_OK) {
                            iView.initData(response.getData().getList(), page == 0);
                        } else {
                            iView.showError(response.getErrorMsg().toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iView.showError(throwable.getMessage());
                    }
                });
    }

    public void collectDoc(final int position, final DoclistBean bean) {
        CollectUtils.collectDoc(iView, position, bean);
    }

    public void uncollectDoc(final int position, final DoclistBean bean) {
        CollectUtils.uncollectoc(iView, position, bean);
    }
}
