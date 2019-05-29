package com.example.examApp.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.examApp.data.database.DatabaseHero;
import com.example.examApp.data.database.DbHelper;
import com.example.examApp.data.network.ApiHelper;
import com.example.examApp.data.sharedPref.SharedPreferencesHelper;

import java.util.List;

import retrofit2.Retrofit;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *DataManger singleton design pattern the application model
 * communicate with the database shared preferences and the api
 */
public class DataManager {

    private static DataManager instance;
    private DbHelper mDbHelper;
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private ApiHelper mApiHelper;


    private DataManager(Context context) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
        mDbHelper = DbHelper.getInstance(context);
        mApiHelper = ApiHelper.getInstance();
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    public Retrofit getRetroFit() {
        return mApiHelper.getRetrofit();
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


}