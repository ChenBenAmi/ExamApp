package com.example.examApp.ui.home.recyclerview;


import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import com.example.examApp.data.database.DatabaseHero;
import com.example.examApp.data.network.JsonHero;
import com.example.examApp.ui.base.MvpPresenter;
import com.example.examApp.ui.home.HomeMvpView;
import com.example.examApp.ui.home.recyclerview.HeroesAdapter.HeroViewHolder;
import java.util.List;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Heroes Presenter interface with all its methods
 */
interface HeroesMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void onBind(HeroViewHolder songViewHolder, int position, List<DatabaseHero> databaseHeroes);

    void setRecyclerView(RecyclerView recyclerView, HeroesAdapter heroesAdapter);

    void buildRetroFit(RecyclerView recyclerView, HeroesAdapter heroesAdapter);

    void insertToDb(List<JsonHero> jsonHeroes, int position);

    String getImage();

    String getTitle();

    void onItemClicked(int position,String title);

    LiveData<List<DatabaseHero>> getAllHeroes();
}
