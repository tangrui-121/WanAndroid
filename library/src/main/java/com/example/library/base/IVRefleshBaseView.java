package com.example.library.base;

public interface IVRefleshBaseView<T> extends IVBaseView {
    void refreshView(boolean isRefresh, T object);
    void showError(String msg);
}
