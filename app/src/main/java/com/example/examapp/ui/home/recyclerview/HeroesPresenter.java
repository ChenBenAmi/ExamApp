package com.example.examapp.ui.home.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.examapp.data.DataManager;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.network.ApiInterface;
import com.example.examapp.data.network.Hero;
import com.example.examapp.ui.base.BasePresenter;
import com.example.examapp.ui.home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
//                Toast.makeText(, "Something wrong", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"something is wrong");
            }
        });
    }



}
