package com.example.examapp.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.database.DbHelper;
import com.example.examapp.data.network.ApiHelper;
import com.example.examapp.data.sharedPref.SharedPreferencesHelper;

import java.util.List;

public class DataManager {

    private static DataManager instance;
    private final Context mContext;
    private DbHelper mDbHelper;
    private ApiHelper mApiHelper;
    private SharedPreferencesHelper mSharedPreferencesHelper;


    private DataManager(Context context) {
        mContext = context;
        mApiHelper = new ApiHelper();
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
        mDbHelper = DbHelper.getInstance(mContext);
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    public DbHelper getDbHelper() {
        return mDbHelper;
    }

    public void insertHero(DatabaseHero databaseHero) {
        mDbHelper.taskDao().insert(databaseHero);
    }

    public boolean checkFavoriteState(String title) {
        return mDbHelper.taskDao().favoriteState(title);
    }

    public DatabaseHero getHeroByName(String name) {
        return mDbHelper.taskDao().getHeroByName(name);
    }

    public void updateHero(DatabaseHero databaseHero) {
        mDbHelper.taskDao().updateList(databaseHero);
    }

    public void listToFalse() {
        mDbHelper.taskDao().listToFalse();
    }

    public List<DatabaseHero> getAllHeroes() {
        return mDbHelper.taskDao().getAll();
    }

    public LiveData<List<DatabaseHero>> loadAllHeroes() {
        return mDbHelper.taskDao().loadAllHeroes();
    }

    public void clearTable() {
        mDbHelper.taskDao().clearTable();

    }




    public String getAppTitle() {
        return mSharedPreferencesHelper.getAppTitle();
    }

    public void setAppTitle(String appTitle) {
        mSharedPreferencesHelper.setAppTitle(appTitle);
    }

    public String getAppImage() {
        return mSharedPreferencesHelper.getAppImage();
    }

    public void setAppImage(String appImage) {
        mSharedPreferencesHelper.setAppImage(appImage);
    }

    public void clearSharedPref() {
        mSharedPreferencesHelper.clear();
    }


}