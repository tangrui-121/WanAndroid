package com.example.mrtang.wanandroid.moudle.collectutils;

import com.example.library.base.IVBaseView;
import com.example.library.bean.DoclistBean;
import com.example.library.http.Result;
import com.example.library.okhttp.ApiServices;
import com.example.library.okhttp.DataResponse;
import com.example.library.okhttp.RetrofitManager;
import com.example.library.okhttp.RxSchedulers;
import com.example.mrtang.wanandroid.fragment.homefragment.IHomeView;
import com.example.mrtang.wanandroid.fragment.knowledgefragment.knowlist.IKnowlistView;
import com.example.mrtang.wanandroid.moudle.collect.ICollectView;
import com.example.mrtang.wanandroid.moudle.webview.ICommonWebviewView;
import io.reactivex.functions.Consumer;

/**
 * Created by Mr'Tang on 2018/6/5.
 */

public class CollectUtils {
    public static void collectDoc(final IVBaseView iView, final int position, final DoclistBean bean) {
        RetrofitManager.create(ApiServices.class)
                .collectDoc(bean.getId())
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(iView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == Result.RESULT_OK) {
                            bean.setCollect(!bean.getCollect());
                            if (iView instanceof IHomeView) {
                                ((IHomeView) iView).collectArticleSuccess(position, bean);
                            } else if (iView instanceof ICollectView) {
                                ((ICollectView) iView).collectArticleSuccess(position, bean);
                            } else if (iView instanceof ICommonWebviewView) {
                                ((ICommonWebviewView) iView).after_collect(bean);
                            } else if (iView instanceof IKnowlistView) {
                                ((IKnowlistView) iView).collectArticleSuccess(position, bean);
                            }
                            iView.showError("收藏成功");
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

    public static void uncollectDoc(final IVBaseView iView, final int position, final DoclistBean bean) {
        RetrofitManager.create(ApiServices.class)
                .uncollectDoc(bean.getId())
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(iView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == Result.RESULT_OK) {
                            bean.setCollect(!bean.getCollect());
                            if (iView instanceof IHomeView) {
                                ((IHomeView) iView).collectArticleSuccess(position, bean);
                            } else if (iView instanceof ICollectView) {
                                ((ICollectView) iView).collectArticleSuccess(position, bean);
                            } else if (iView instanceof ICommonWebviewView) {
                                ((ICommonWebviewView) iView).after_collect(bean);
                            } else if (iView instanceof IKnowlistView) {
                                ((IKnowlistView) iView).collectArticleSuccess(position, bean);
                            }
                            iView.showError("取消收藏成功");
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

    public static void uncollectoc(final IVBaseView iView, final int position, final DoclistBean bean) {
        RetrofitManager.create(ApiServices.class)
                .uncollectdoc(bean.getId(), bean.getOriginId())
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(iView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == Result.RESULT_OK) {
                            bean.setCollect(!bean.getCollect());
                            if (iView instanceof ICollectView) {
                                ((ICollectView) iView).collectArticleSuccess(position, bean);
                            } else if (iView instanceof ICommonWebviewView) {
                                ((ICommonWebviewView) iView).after_collect(bean);
                            }
                            iView.showError("取消收藏成功");
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

}
