package com.example.examapp.ui.home.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.examapp.data.DataManager;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.network.Hero;
import com.example.examapp.ui.base.BasePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HeroesPresenter<V extends HeroesMvpView> extends BasePresenter<V> implements HeroesMvpPresenter<V> {

    private static final String TAG = "HeroesPresenter";
    DataManager mDataManager;
    private Context mContext;
    private List<Hero> values;


    public HeroesPresenter(Context context) {
        super(context);
        this.mContext = context;
        mDataManager = DataManager.getInstance(context);

    }


    @Override
    public void onBind(HeroesAdapter.HeroViewHolder heroViewHolder, int position, List<DatabaseHero> databaseHeroes, List<Hero> jsonHeroes) {
        if (jsonHeroes != null) {
            Hero jsonHero = jsonHeroes.get(position);
            String name = jsonHero.getTitle();
            heroViewHolder.mHeroName.setText(name);
            List<String> abilitiesList = jsonHero.getAbilities();
            StringBuilder builder = new StringBuilder();
            for (String ability : abilitiesList) {
                builder.append(ability + ", ");
            }
            heroViewHolder.mHeroAbilities.setText(builder.toString());
            Glide.with(mContext)
                .load(jsonHero.getImage())
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.overrideOf(250,250))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(heroViewHolder.mHeroImage);
            heroViewHolder.mFavoriteView.setVisibility(View.INVISIBLE);

        }


//        DatabaseHero databaseHero = databaseHeroes.get(position);
//        heroViewHolder.mHeroName.setText(databaseHero.getTitle());
//        heroViewHolder.mHeroAbilities.setText(databaseHero.getAbilities());
//        Glide.with(mContext)
//                .load(databaseHero.getImageUrl())
//                .apply(RequestOptions.circleCropTransform())
//                .apply(RequestOptions.overrideOf(250,250))
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(heroViewHolder.mHeroImage);
//        if (!databaseHero.getFavorite()) {
//            heroViewHolder.mFavoriteView.setVisibility(View.INVISIBLE);
//        }

    }

    @Override
    public int getViewCount() {
        return 11;
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView, HeroesAdapter heroesAdapter) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(heroesAdapter);

    }

    @Override
    public void getList(List<Hero> repos) {
        values = repos;
        Log.i(TAG, values.toString());
    }


}
