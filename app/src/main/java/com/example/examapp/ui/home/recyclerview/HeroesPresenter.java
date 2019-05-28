package com.example.examapp.ui.home.recyclerview;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.examapp.data.DataManager;
import com.example.examapp.data.database.AppExecutors;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.database.DbHelper;
import com.example.examapp.data.network.ApiInterface;
import com.example.examapp.data.network.Hero;
import com.example.examapp.ui.home.HomeMvpView;
import com.example.examapp.ui.image.ViewImage;
import com.example.examapp.ui.base.BasePresenter;

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
    private Context mContext;
    private DbHelper mDbHelper;
    private List<DatabaseHero> heroList = new ArrayList<>();


    public HeroesPresenter(Context context) {
        super(context);
        this.mContext = context;
        DataManager mDataManager = DataManager.getInstance(context);
        mDbHelper = DbHelper.getInstance(mContext);
    }


    @Override
    public void onBind(final HeroesAdapter.HeroViewHolder heroViewHolder, final int position, final List<DatabaseHero> databaseHeroes, final List<Hero> jsonHeroes) {
     AppExecutors.getInstance().diskIO().execute(new Runnable() {
         @Override
         public void run() {
             if (mDbHelper.taskDao().getHeroByName("Drax the Destroyer") == null) {
                 Log.i(TAG, "FROM JSON");
                 if (jsonHeroes != null) {
                     final Hero jsonHero = jsonHeroes.get(position);
                     String name = jsonHero.getTitle();
                     heroViewHolder.mHeroName.setText(name);
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
                     heroViewHolder.mHeroAbilities.setText(builder.toString());
                     AppExecutors.getInstance().mainThread().execute(new Runnable() {
                         @Override
                         public void run() {
                             Glide.with(mContext)
                                     .load(jsonHero.getImage())
                                     .apply(RequestOptions.circleCropTransform())
                                     .apply(RequestOptions.overrideOf(250, 250))
                                     .into(heroViewHolder.mHeroImage);
                         }
                     });

                     heroViewHolder.mFavoriteView.setVisibility(View.INVISIBLE);
                     final DatabaseHero databaseHero = new DatabaseHero(name, builder.toString(), jsonHero.getImage(), false);
                     heroList.add(databaseHero);
                     Log.i(TAG, "THE SIZE IS " + heroList.size());
                     if (heroList.size() == 11) {
                         Log.i(TAG, "HELLO");
                         insertToDb();
                     }
                 }
             } else {
                 Log.i(TAG, "From DB");
                 Log.i(TAG,"THE POSITION IS"+position);
                 AppExecutors.getInstance().mainThread().execute(new Runnable() {
                     @Override
                     public void run() {
                         if (databaseHeroes.size()>0) {


                         final DatabaseHero databaseHero = databaseHeroes.get(position);
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


                     }}
                 });

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
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://heroapps.co.il/employee-tests/android/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<List<Hero>> call = client.getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> repos = response.body();
                recyclerView.setAdapter(heroesAdapter);
                heroesAdapter.getList(repos);
                heroesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Log.i(TAG, "something is wrong");
            }
        });
    }

    @Override
    public void insertToDb() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < heroList.size(); i++) {

                    mDbHelper.taskDao().insert(heroList.get(i));
                    Log.i(TAG, "THIS IS HERO NR " + i +heroList.get(i));
                }
            }
        });


    }

    @Override
    public void deleteDb() {
        heroList.clear();
        Log.i(TAG, "SHOULD BE CLEARED");
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDbHelper.taskDao().clearTable();
            }
        });
        heroList.clear();

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
                if (mDbHelper.taskDao().favoriteState(title)) {
                    Log.i(TAG, "the value is true");
                }
                mDbHelper.taskDao().listToFalse();
                final DatabaseHero databaseHero = mDbHelper.taskDao().getHeroByName(title);
                databaseHero.setFavorite(true);
                mDbHelper.taskDao().updateList(databaseHero);
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
        final LiveData<List<DatabaseHero>> heroList = mDbHelper.taskDao().loadAllHeroes();
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
