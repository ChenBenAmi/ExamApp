package com.example.examapp.ui.home;


import com.example.examapp.ui.base.MvpView;

public interface HomeMvpView extends MvpView {


    void setUpImageFromSharedPrefs();

    void setUpTitleFromSharedPrefs();

    void setUpImageFromDb(String imageUrl);

    void setUpTitleFromDb(String title);

    void setObservable();



}
