package com.example.animetracker.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnimeListDao {
    @Insert
    void insert(AnimeDatabaseEntry entry);

    @Delete
    void delete(AnimeDatabaseEntry entry);

    @Update
    void update(AnimeDatabaseEntry entry);

    @Query("SELECT * FROM animeList")
    LiveData<List<AnimeDatabaseEntry>> getAllAnimeListEntries();

    @Query("SELECT * FROM animeList WHERE id = :id LIMIT 1")
    LiveData<AnimeDatabaseEntry> getAnimeListEntryByName(String id);
}
