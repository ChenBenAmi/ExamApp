package com.example.examapp.data;

import android.content.Context;

import com.example.examapp.data.database.DbHelper;
import com.example.examapp.data.network.ApiHelper;
import com.example.examapp.data.sharedPref.SharedPreferencesHelper;

public class DataManager {

    private static DataManager instance;
    private final Context mContext;
    private DbHelper mDbHelper;
    private ApiHelper mApiHelper;
    private SharedPreferencesHelper mSharedPreferencesHelper;


    private DataManager(Context context) {
        mContext = context;
        mApiHelper=new ApiHelper();
        mSharedPreferencesHelper=new SharedPreferencesHelper(context);
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