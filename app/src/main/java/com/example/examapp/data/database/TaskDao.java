package com.example.examapp.data.database;

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

    @Query("UPDATE HeroList SET mFavorite = :favorite WHERE mListPosition = :listPosition")
    int updateFavorite(int listPosition, boolean favorite);

    @Query("SELECT  mListPosition=:id FROM herolist WHERE mFavorite= 1")
    boolean favoriteState(int id);

    @Query("SELECT * FROM HeroList WHERE mFavorite=1")
    DatabaseHero getHeroByBoolean();

    @Query("Delete FROM Herolist")
    void clearTable();

    @Query("UPDATE HeroList SET mFavorite=0")
    void listToFalse();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateList(DatabaseHero databaseHero);

    @Query("SELECT * FROM HeroList WHERE mListPosition=:position")
    DatabaseHero getHeroByPosition(int position);

    @Query("SELECT * FROM Herolist")
    LiveData<List<DatabaseHero>> loadAllHeroes();

    @Query("SELECT * FROM HeroList")
    boolean dataExist();


}
