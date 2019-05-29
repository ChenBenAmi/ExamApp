package com.example.examApp.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.examApp.R;
import com.example.examApp.data.database.DatabaseHero;
import com.example.examApp.ui.home.recyclerview.HeroesAdapter;
import com.example.examApp.ui.home.recyclerview.HeroesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeMvpView, HeroesAdapter.listItemClickListener {

    private static final String TAG = "HomeActivity";
    private HeroesPresenter<HomeActivity> mHeroesPresenter;
    private HeroesAdapter mHeroesAdapter;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mApp_bar_layout;

    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;

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

        mHeroesPresenter = new HeroesPresenter<>(HomeActivity.this);
        mHeroesPresenter.onAttach(HomeActivity.this);
        mHeroesAdapter = new HeroesAdapter(this, this);

        mHeroesPresenter.setRecyclerView(mRecyclerView, mHeroesAdapter);
        mHeroesPresenter.buildRetroFit(mRecyclerView, mHeroesAdapter);
        setObservable();

        setUpTitleFromSharedPrefs();
        setUpImageFromSharedPrefs();


    }

    @Override
    public void onListItemClick(final int position, final String title) {
        mHeroesPresenter.onItemClicked(position, title);
    }

    @Override
    public void setObservable() {
        if (mHeroesPresenter.getAllHeroes() !=null) {
            final LiveData<List<DatabaseHero>> heroList=mHeroesPresenter.getAllHeroes();
            Log.i(TAG,heroList.toString());
            heroList.observe(this, new Observer<List<DatabaseHero>>() {
                @Override
                public void onChanged(@Nullable List<DatabaseHero> list) {
                    mHeroesAdapter.setHeroEntries(list);
                }
            });
        }


    }


    @Override
    public void setUpImageFromSharedPrefs() {
        Glide.with(getApplicationContext())
                .load(mHeroesPresenter.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mTitleImageView);

    }

    @Override
    public void setUpTitleFromSharedPrefs() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mHeroesPresenter.getTitle());
        }
    }

    @Override
    public void setUpImageFromDb(final String imageUrl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mTitleImageView);
            }
        });

    }

    @Override
    public void setUpTitleFromDb(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNestedScrollView.scrollTo(0, 0);
                mApp_bar_layout.setExpanded(true);
                getSupportActionBar().setTitle(title);
            }
        });

    }
}
