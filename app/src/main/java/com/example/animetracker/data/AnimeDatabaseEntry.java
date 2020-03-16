package com.example.animetracker.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "animeList")
public class AnimeDatabaseEntry implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;

    public String showName;

    @ColumnInfo(defaultValue = "WATCHING")
    public String watchStatus;

    @ColumnInfo(defaultValue = "0")
    public int showScore;

    @ColumnInfo(defaultValue = "0")
    public int episodesWatched;

    @ColumnInfo(defaultValue = "1")
    public int totalEpisodes;
}
