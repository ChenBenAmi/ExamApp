package com.example.examapp.data.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {


    private static final String MY_PREFS = "MY_PREFS";
    private static final String APP_TITLE = "APP_TITLE";
    private static final String APP_IMAGE="APP_IMAGE";

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);

    }

    public String getAppTitle() {
        return mSharedPreferences.getString(APP_TITLE,"Exam App");
    }

    public void setAppTitle(String appTitle) {
        mSharedPreferences.edit().putString(APP_TITLE,appTitle).apply();
    }

    public String getAppImage() {
        return mSharedPreferences.getString(APP_IMAGE,"");
    }

    public void setAppImage(String appImage) {
        mSharedPreferences.edit().putString(APP_IMAGE,appImage).apply();
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
