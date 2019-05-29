package com.example.examApp.ui.home;


import com.example.examApp.ui.base.MvpView;

public interface HomeMvpView extends MvpView {


    void setUpImageFromSharedPrefs();

    void setUpTitleFromSharedPrefs();

    void setUpImageFromDb(String imageUrl);

    void setUpTitleFromDb(String title);

    void setObservable();



}
