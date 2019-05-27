package com.example.examapp.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

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

    private HeroesPresenter mHeroesPresenter;
    private HeroesAdapter mHeroesAdapter;
    private DbHelper dbHelper;





    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(getResources().getString(R.string.hero_list));
        ButterKnife.bind(this);
        dbHelper= DbHelper.getInstance(getApplicationContext());
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
                List<DatabaseHero> heroList=dbHelper.taskDao().loadAllHeroes();
                mHeroesAdapter.setTasks(heroList);
            }
        });
    }

    @Override
    public void onListItemClick(int position) {

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
                final DatabaseHero databaseHero = new DatabaseHero(
                        "chen"
                        , "Dummy"
                        , "https://www.americangrit.com/wp-content/uploads/2017/09/wolverine.jpg", false);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper.taskDao().insert(databaseHero);

                    }
                });

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
