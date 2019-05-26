package com.example.examapp.data;
import android.content.Context;

public class DataManager {
    private static  DataManager instance;




    private DataManager(Context context) {

    }

    public  static synchronized DataManager getInstance(Context context){
        if (instance==null) {
            instance = new DataManager(context);
        }
        return instance;
    }
}