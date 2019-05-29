package com.example.examApp.ui.base;

import android.content.Context;

import com.example.examApp.data.DataManager;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Base Presenter class
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;


    public BasePresenter(Context context) {
        Context mContext = context;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    public V getMvpView() {
        return mMvpView;
    }


}
