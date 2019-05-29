package com.example.examApp.ui.splash;


import com.example.examApp.ui.base.MvpPresenter;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *
 */
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void openHomeActivity();


}