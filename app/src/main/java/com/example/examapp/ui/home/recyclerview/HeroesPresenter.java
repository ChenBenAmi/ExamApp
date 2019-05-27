package com.example.examapp.ui.home.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.examapp.data.DataManager;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.ui.base.BasePresenter;

import java.util.List;

public class HeroesPresenter<V extends HeroesMvpView> extends BasePresenter<V> implements HeroesMvpPresenter<V> {


    DataManager mDataManager;
    private Context mContext;


    public HeroesPresenter(Context context) {
        super(context);
        this.mContext = context;
        mDataManager = DataManager.getInstance(context);


    }


    @Override
    public void onBind(HeroesAdapter.HeroViewHolder heroViewHolder, int position,List<DatabaseHero> list) {
        DatabaseHero databaseHero = list.get(position);
        heroViewHolder.mHeroName.setText(databaseHero.getTitle());
        heroViewHolder.mHeroAbilities.setText(databaseHero.getAbilities());

        Glide.with(mContext)
                .load(databaseHero.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.overrideOf(250,250))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(heroViewHolder.mHeroImage);
        if (!databaseHero.getFavorite()) {
            heroViewHolder.mFavoriteView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getViewCount() {
        if (HeroesAdapter.getHeroList()==null) {
            return 0;
        }
        return HeroesAdapter.getHeroList().size();
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView, HeroesAdapter heroesAdapter) {
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(heroesAdapter);
    }


}
