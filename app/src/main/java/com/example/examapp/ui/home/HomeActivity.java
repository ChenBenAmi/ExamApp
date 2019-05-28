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
        ButterKnife.bind(this);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        mHeroesPresenter = new HeroesPresenter(HomeActivity.this);
        mHeroesPresenter.onAttach(HomeActivity.this);
        mHeroesAdapter = new HeroesAdapter(this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mHeroesPresenter.buildRetroFit(mRecyclerView, mHeroesAdapter);
        final LiveData<List<DatabaseHero>> heroList = dbHelper.taskDao().loadAllHeroes();
        heroList.observe(this, new Observer<List<DatabaseHero>>() {
            @Override
            public void onChanged(@Nullable List<DatabaseHero> list) {
                mHeroesAdapter.setHeroEntries(list);
            }
        });

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
//    }

    @Override
    public void onListItemClick(final int position, final String title) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
                public void run() {
                    boolean favorite = true;
                    Log.i(TAG,"the title is "+title);
                    if (dbHelper.taskDao().favoriteState(title)) {
                        favorite = false;
                        Log.i(TAG, "the value is true");
                    }
                dbHelper.taskDao().listToFalse();
                DatabaseHero databaseHero = dbHelper.taskDao().getHeroByName(title);
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
            case R.id.delete:
                mHeroesPresenter.buildRetroFit(mRecyclerView,mHeroesAdapter);
                mHeroesPresenter.deleteDb();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
