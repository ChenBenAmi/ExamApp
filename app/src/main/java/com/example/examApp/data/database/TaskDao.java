package com.example.examApp.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface TaskDao {


    @Insert
    void insert(DatabaseHero databaseHero);

    @Query("SELECT  mTitle=:title FROM herolist WHERE mFavorite= 1")
    boolean favoriteState(String title);

    @Query("SELECT * FROM HeroList WHERE mTitle=:title")
    DatabaseHero getHeroByName(String title);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateList(DatabaseHero databaseHero);

    @Query("UPDATE HeroList SET mFavorite=0")
    void listToFalse();

    @Query("SELECT * FROM herolist")
    List<DatabaseHero> getAll();

    @Query("SELECT * FROM Herolist")
    LiveData<List<DatabaseHero>> loadAllHeroes();



}
