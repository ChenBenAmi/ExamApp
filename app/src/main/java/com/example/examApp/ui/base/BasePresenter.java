package com.example.examApp.ui.base;

import android.content.Context;

import com.example.examApp.data.DataManager;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    DataManager mDataManager;
    private V mMvpView;


    public BasePresenter(Context context) {
        Context context1 = context;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    public V getMvpView() {
        return mMvpView;
    }


}
