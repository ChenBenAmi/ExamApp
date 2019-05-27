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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(DatabaseHero databaseHero);

    @Query("Delete FROM Herolist")
    void clearTable();

    @Query("SELECT * FROM Herolist")
    List<DatabaseHero> loadAllHeroes();


}
