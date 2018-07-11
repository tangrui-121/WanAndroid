package com.example.library.base;

public abstract class BasicPresenter implements IVBasePresenter {
    private IVBaseView iView;
    public BasicPresenter(IVBaseView iView){
        this.iView = iView;
    }
}
