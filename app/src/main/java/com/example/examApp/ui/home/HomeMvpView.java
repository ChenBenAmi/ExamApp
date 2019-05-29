package com.example.examApp.ui.home;


import com.example.examApp.ui.base.MvpView;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Home view interface
 */
public interface HomeMvpView extends MvpView {


    void setUpImageFromSharedPrefs();

    void setUpTitleFromSharedPrefs();

    void setUpImageFromDb(String imageUrl);

    void setUpTitleFromDb(String title);

    void setObservable();



}
