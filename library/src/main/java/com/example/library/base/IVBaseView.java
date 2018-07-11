package com.example.library.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface IVBaseView {
    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();

    void showError(String msg);
}
