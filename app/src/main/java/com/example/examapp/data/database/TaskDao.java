package com.example.examapp.data.database;

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

    @Query("UPDATE HeroList SET mFavorite=0")
    void listToFalse();

    @Query("SELECT * FROM HeroList WHERE mFavorite=1")
    DatabaseHero getHeroByPosition();

    @Query("Delete FROM Herolist")
    void clearTable();

    @Query("SELECT * FROM Herolist")
    List<DatabaseHero> loadAllHeroes();


}
