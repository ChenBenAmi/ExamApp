package com.example.examapp.data;

import android.content.Context;

import com.example.examapp.data.database.DbHelper;
import com.example.examapp.data.network.ApiHelper;

public class DataManager {

    private static DataManager instance;
    private final Context mContext;
    private DbHelper mDbHelper;
    private ApiHelper mApiHelper;


    private DataManager(Context context) {
        mContext = context;
        mApiHelper=new ApiHelper();
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

}