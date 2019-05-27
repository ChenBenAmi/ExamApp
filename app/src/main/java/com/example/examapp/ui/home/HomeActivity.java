package com.example.examapp.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.examapp.R;
import com.example.examapp.data.database.AppExecutors;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.database.DbHelper;
import com.example.examapp.data.network.ApiInterface;
import com.example.examapp.data.network.Hero;
import com.example.examapp.ui.home.recyclerview.HeroesAdapter;
import com.example.examapp.ui.home.recyclerview.HeroesPresenter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements HomeMvpView, HeroesAdapter.listItemClickListener {

    private static final String TAG = "HomeActivity";
    private HeroesPresenter mHeroesPresenter;
    private HeroesAdapter mHeroesAdapter;
    private DbHelper dbHelper;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.title_image)
    ImageView mTitleImageView;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        mHeroesPresenter = new HeroesPresenter(HomeActivity.this);
        mHeroesPresenter.onAttach(HomeActivity.this);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://heroapps.co.il/employee-tests/android/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<List<Hero>> call = client.getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> repos =response.body();
                Log.i(TAG, response.body().get(0).toString());
                mRecyclerView.setAdapter(mHeroesAdapter = new HeroesAdapter(HomeActivity.this, HomeActivity.this));
                mHeroesAdapter.getList(repos);
                mHeroesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });


//        final LiveData<List<DatabaseHero>> heroList = dbHelper.taskDao().loadAllHeroes();
//        heroList.observe(this, new Observer<List<DatabaseHero>>() {
//            @Override
//            public void onChanged(@Nullable List<DatabaseHero> list) {
//                mHeroesAdapter.setTasks(list);
//            }
//        });

    }

    //    public void setUpTitle (){
//        AppExecutors.getInstance().mainThread().execute(new Runnable() {
//            @Override
//            public void run() {
//                DatabaseHero databaseHero = dbHelper.taskDao().getHeroByBoolean();
////                if ( databaseHero!= null) {
////                    Glide.with(getApplicationContext())
////                            .load(databaseHero.getImageUrl())
////                            .transition(DrawableTransitionOptions.withCrossFade())
////                            .into(mTitleImageView);
////                    mToolBar.setTitle(databaseHero.getTitle());
////                }
//            }
//        });
//
//
//    }
    @Override
    public void onListItemClick(final int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                boolean favorite = true;
                if (dbHelper.taskDao().favoriteState(position)) {
                    favorite = false;
                    Log.i(TAG, "the value is true");
                }
                dbHelper.taskDao().listToFalse();
                DatabaseHero databaseHero = dbHelper.taskDao().getHeroByPosition(position);
                databaseHero.setFavorite(true);
                dbHelper.taskDao().updateList(databaseHero);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.dummy_data:
                int listposition = mHeroesAdapter.getItemCount();
                final DatabaseHero databaseHero = new DatabaseHero(
                        "Dummy data"
                        , "Dummy data"
                        , "https://www.americangrit.com/wp-content/uploads/2017/09/wolverine.jpg"
                        , false, listposition);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper.taskDao().insert(databaseHero);

                    }
                });

                return true;
            case R.id.delete:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper.taskDao().clearTable();
                    }
                });
                return true;
            case R.id.negative:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper.taskDao().listToFalse();
                    }
                });

        }
        return super.onOptionsItemSelected(item);
    }


}
