package com.example.examapp.ui.home.recyclerview;


import android.support.v7.widget.RecyclerView;

import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.ui.base.MvpPresenter;

import java.io.IOException;
import java.util.List;

public interface HeroesMvpPresenter<V extends HeroesMvpView> extends MvpPresenter<V> {

    void onBind(HeroesAdapter.HeroViewHolder songViewHolder, int position, List<DatabaseHero> list) throws IOException;

    int getViewCount();

    void setRecyclerView(RecyclerView recyclerView, HeroesAdapter heroesAdapter);



}
