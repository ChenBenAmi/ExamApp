package com.example.examApp.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Database singleton to store the json objects in memory using room library
 */
@Database(entities = {DatabaseHero.class},version = 4,exportSchema = false)
public abstract class DbHelper extends RoomDatabase {

    private static final Object Lock = new Object();
    private  static final String DATABASE_NAME = "HeroList";
    private  static DbHelper instance;

    public static DbHelper getInstance (Context context){
        if(instance == null){
            synchronized (Lock){
                instance = Room.databaseBuilder(context
                        .getApplicationContext(), DbHelper
                        .class, DbHelper.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }


    public  abstract  TaskDao taskDao();

}
