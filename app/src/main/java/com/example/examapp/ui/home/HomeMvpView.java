package com.example.examapp.ui.home;


import android.arch.lifecycle.LiveData;

import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.ui.base.MvpView;

import java.util.List;

public interface HomeMvpView extends MvpView {


    void setUpImageFromSharedPrefs();

    void setUpTitleFromSharedPrefs();

    void setUpImageFromDb(String imageUrl);

    void setUpTitleFromDb(String title);

    void setObservable();

}
