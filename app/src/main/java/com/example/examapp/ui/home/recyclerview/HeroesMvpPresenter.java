package com.example.examapp.ui.home.recyclerview;


import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;

import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.network.JsonHero;
import com.example.examapp.ui.base.MvpPresenter;
import com.example.examapp.ui.home.HomeMvpView;
import com.example.examapp.ui.home.recyclerview.HeroesAdapter.HeroViewHolder;

import java.io.IOException;
import java.util.List;

public interface HeroesMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void onBind(HeroViewHolder songViewHolder, int position, List<DatabaseHero> databaseHeroes) throws IOException;

    int getViewCount();

    void setRecyclerView(RecyclerView recyclerView, HeroesAdapter heroesAdapter);

    void buildRetroFit(RecyclerView recyclerView, HeroesAdapter heroesAdapter);

    void insertToDb(List<JsonHero> jsonHeroes, int position);

    void deleteDb();

    String getImage();

    String getTitle();

    void onItemClicked(int position,String title);

    LiveData<List<DatabaseHero>> getAllHeroes();
}
