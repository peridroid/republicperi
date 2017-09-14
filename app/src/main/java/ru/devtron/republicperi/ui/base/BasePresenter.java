package ru.devtron.republicperi.ui.base;


import android.support.annotation.Nullable;

public abstract class BasePresenter<T extends BaseView> {
    private T mView;

    public void takeView(T view) {
        this.mView = view;
    }

    public void dropView() {
        mView = null;
    }

    public abstract void initView();

    @Nullable
    public T getView() {
        return mView;
    }

    public abstract void destroy();
}
