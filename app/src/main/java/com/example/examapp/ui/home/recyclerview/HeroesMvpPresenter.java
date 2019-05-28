package com.example.examapp.ui.home.recyclerview;


import android.support.v7.widget.RecyclerView;

import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.network.Hero;
import com.example.examapp.ui.base.MvpPresenter;
import com.example.examapp.ui.base.MvpView;
import com.example.examapp.ui.home.HomeMvpView;

import java.io.IOException;
import java.util.List;

public interface HeroesMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void onBind(HeroesAdapter.HeroViewHolder songViewHolder, int position, List<DatabaseHero> databaseHeroes,List<Hero> jsonHeroes) throws IOException;

    int getViewCount();

    void setRecyclerView(RecyclerView recyclerView, HeroesAdapter heroesAdapter);

    void buildRetroFit(RecyclerView recyclerView, HeroesAdapter heroesAdapter);

    void insertToDb();

    void deleteDb();

    String getImage();

    String getTitle();

    void onItemClicked(int position,String title);
}
