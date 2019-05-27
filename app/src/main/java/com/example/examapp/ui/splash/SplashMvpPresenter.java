package com.example.examapp.ui.splash;


import com.example.examapp.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void openHomeActivity();


}