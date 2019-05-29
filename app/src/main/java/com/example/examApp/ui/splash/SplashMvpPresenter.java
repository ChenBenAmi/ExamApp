package com.example.examApp.ui.splash;


import com.example.examApp.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void openHomeActivity();


}