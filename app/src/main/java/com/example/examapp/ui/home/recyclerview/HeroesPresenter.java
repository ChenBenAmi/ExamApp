package com.example.examapp.ui.home.recyclerview;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.examapp.data.DataManager;
import com.example.examapp.data.database.AppExecutors;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.network.ApiInterface;
import com.example.examapp.data.network.JsonHero;
import com.example.examapp.ui.base.BasePresenter;
import com.example.examapp.ui.home.HomeMvpView;
import com.example.examapp.ui.home.recyclerview.HeroesAdapter.HeroViewHolder;
import com.example.examapp.ui.image.ViewImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HeroesPresenter<V extends HomeMvpView> extends BasePresenter<V> implements HeroesMvpPresenter<V> {

    private static final String TAG = "HeroesPresenter";
    public static final String HERO_URL = "HERO_URL";
    public static final String HERO_NAME = "HERO_NAME";
    private DataManager mDataManager;
    private Context mContext;
    private List<DatabaseHero> mHeroList = new ArrayList<>();


    public HeroesPresenter(Context context) {
        super(context);
        this.mContext = context;
        mDataManager = DataManager.getInstance(context);
    }


    @Override
    public void onBind(final HeroViewHolder heroViewHolder, final int position, final List<DatabaseHero> databaseHeroes) {
        Log.i(TAG, "ON BIND TRIGGERED nr " + position);
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                if (databaseHeroes.size() > 0) {
                    final DatabaseHero databaseHero = databaseHeroes.get(position);
                    Log.i(TAG, "this is hero " + databaseHero.toString());
                    heroViewHolder.mHeroName.setText(databaseHero.getTitle());
                    heroViewHolder.mHeroAbilities.setText(databaseHero.getAbilities());
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(mContext)
                                    .load(databaseHero.getImageUrl())
                                    .apply(RequestOptions.circleCropTransform())
                                    .apply(RequestOptions.overrideOf(250, 250))
                                    .into(heroViewHolder.mHeroImage);
                        }
                    });

                    if (!databaseHero.getFavorite()) {
                        heroViewHolder.mFavoriteView.setVisibility(View.INVISIBLE);
                    } else {
                        heroViewHolder.mFavoriteView.setVisibility(View.VISIBLE);
                    }


                }
            }
        });

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
    public void buildRetroFit(final RecyclerView recyclerView, final HeroesAdapter heroesAdapter) {
        Log.i(TAG, "nothing in db getting json");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://heroapps.co.il/employee-tests/android/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<List<JsonHero>> call = client.getHeroes();

        call.enqueue(new Callback<List<JsonHero>>() {
            @Override
            public void onResponse(@NonNull Call<List<JsonHero>> call, @NonNull Response<List<JsonHero>> response) {
                List<JsonHero> repos = response.body();
                recyclerView.setAdapter(heroesAdapter);
                heroesAdapter.putList(repos);
                heroesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<JsonHero>> call, @NonNull Throwable t) {
                Log.i(TAG, "something is wrong");
            }
        });
    }


    @Override
    public void insertToDb(final List<JsonHero> jsonHeroes, final int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                String lastHero="Drax the Destroyer";
                DatabaseHero flagHero = mDataManager.getHeroByName(lastHero);
                if (flagHero == null) {
                    if (jsonHeroes != null) {
                        final JsonHero jsonHero = jsonHeroes.get(position);
                        String name = jsonHero.getTitle();
                        List<String> abilitiesList = jsonHero.getAbilities();
                        StringBuilder builder = new StringBuilder();
                        Iterator<String> iterator = abilitiesList.iterator();
                        int count = 0;
                        while (iterator.hasNext()) {
                            String ability = iterator.next();
                            builder.append(ability);
                            builder.append(", ");
                            count++;
                            if (count == 2) {
                                builder.append("\n");
                            }
                            if (!iterator.hasNext()) {
                                builder.append(ability);
                            }
                        }
                        final DatabaseHero databaseHero = new DatabaseHero(name, builder.toString(), jsonHero.getImage(), false);
                        mHeroList.add(databaseHero);
                        if (mHeroList.size() == 11) {
                            for (int i = 0; i < mHeroList.size(); i++) {

                                mDataManager.insertHero(mHeroList.get(i));

                            }
                        }

                    }

                }
            }
        });
    }



    @Override
    public String getImage() {
        if (DataManager.getInstance(mContext).getAppImage() != null) {
            return DataManager.getInstance(mContext).getAppImage();
        }
        return null;
    }

    @Override
    public String getTitle() {
        if (DataManager.getInstance(mContext).getAppTitle() != null) {
            return DataManager.getInstance(mContext).getAppTitle();
        }
        return null;
    }

    @Override
    public void onItemClicked(int position, final String title) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mDataManager.checkFavoriteState(title)) {
                    Log.i(TAG, "the value is true");
                }
                mDataManager.listToFalse();
                final DatabaseHero databaseHero = mDataManager.getHeroByName(title);
                databaseHero.setFavorite(true);
                mDataManager.updateHero(databaseHero);
                getMvpView().setUpImageFromDb(databaseHero.getImageUrl());
                Log.i(TAG, "the title is " + databaseHero.getTitle());
                getMvpView().setUpTitleFromDb(title);
                DataManager.getInstance(mContext).setAppImage(databaseHero.getImageUrl());
                DataManager.getInstance(mContext).setAppTitle(title);

            }
        });
    }

    @Override
    public LiveData<List<DatabaseHero>> getAllHeroes() {
        final LiveData<List<DatabaseHero>> heroList = mDataManager.loadAllHeroes();
        if (heroList != null) {
            return heroList;
        }
        return null;
    }

    void imageToFull(String url, String heroName) {
        Intent intent = new Intent(mContext, ViewImage.class);
        intent.putExtra(HERO_URL, url);
        intent.putExtra(HERO_NAME, heroName);
        mContext.startActivity(intent);
    }


}
