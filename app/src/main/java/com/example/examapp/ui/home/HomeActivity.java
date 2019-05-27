package com.example.examapp.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.examapp.R;
import com.example.examapp.data.database.AppExecutors;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.database.DbHelper;
import com.example.examapp.ui.home.recyclerview.HeroesAdapter;
import com.example.examapp.ui.home.recyclerview.HeroesPresenter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        setTitle(getResources().getString(R.string.hero_list));
        ButterKnife.bind(this);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        mHeroesPresenter = new HeroesPresenter(this);
        mHeroesPresenter.onAttach(this);
        mHeroesAdapter = new HeroesAdapter(this, this);
        mHeroesPresenter.setRecyclerView(mRecyclerView, mHeroesAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<DatabaseHero> heroList = dbHelper.taskDao().loadAllHeroes();
                final DatabaseHero databaseHero = dbHelper.taskDao().getHeroByPosition();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHeroesAdapter.setTasks(heroList);
                        if (databaseHero != null) {
                            Glide.with(getApplicationContext())
                                    .load(databaseHero.getImageUrl())
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(mTitleImageView);
                            mToolBar.setTitle(databaseHero.getTitle());
                        }

                    }
                });

            }
        });
    }

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
                dbHelper.taskDao().updateFavorite(position, favorite);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mHeroesAdapter.notifyDataSetChanged();

                    }
                });
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
                        "chen"
                        , "Dummy"
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
