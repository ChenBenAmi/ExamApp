package com.example.examapp.ui.home;


import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;

import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.ui.base.MvpView;
import com.example.examapp.ui.home.recyclerview.HeroesAdapter;

public interface HomeMvpView extends MvpView {


    void setUpImageFromSharedPrefs();

    void setUpTitleFromSharedPrefs();

    void setUpImageFromDb(String imageUrl);

    void setUpTitleFromDb(String title);

    void setObservable();

    RecyclerView getRecyclerView();

    HeroesAdapter getHeroesAdapter();


}
