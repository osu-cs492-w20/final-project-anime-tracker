package com.example.animetracker.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "animeList")
public class AnimeDatabaseEntry extends AnimeItem implements Serializable {
    @PrimaryKey
    @NonNull
    public String _id;

    @ColumnInfo(defaultValue = "WATCHING")
    public String watchStatus;

    @ColumnInfo(defaultValue = "0")
    public int showScore;

    @ColumnInfo(defaultValue = "0")
    public int episodesWatched;

//    public String synopsis;
//    public String link;
//    public String averageRating;
//    public int popularityRank;
//    public int ratingRank;
//    public String showType;
//    public String status;
//    public int episodeCount;
//    public int episodeLength;
//    public String youtubeVideoId;
//    public String title;          //english title
//    public String en_jp;       //japanese title in en
//    public String ja_jp;       //japanese title in jp
//    public String tiny;

    public AnimeDatabaseEntry() {
        super();
    }

    public void setAnimeDatabaseEntry(AnimeItem animeItem) {
        this.id = animeItem.id;

        this._id = animeItem.id;

        this.synopsis = animeItem.synopsis;
        this.link = animeItem.link;
        this.averageRating = animeItem.averageRating;
        this.popularityRank = animeItem.popularityRank;
        this.ratingRank = animeItem.ratingRank;
        this.showType = animeItem.showType;
        this.status = animeItem.status;
        this.episodeCount = animeItem.episodeCount;
        this.episodeLength = animeItem.episodeLength;
        this.youtubeVideoId = animeItem.youtubeVideoId;
        this.title = animeItem.title;          //english title
        this.en_jp = animeItem.en_jp;       //japanese title in en
        this.ja_jp = animeItem.ja_jp;       //japanese title in jp
        this.tiny = animeItem.tiny;
    }

    public void set_id(String id) {
        this._id = id;
    }

    public void setWatchStatus(String watchStatus) {
        this.watchStatus = watchStatus;
    }

    public void setShowScore(int showScore) {
        this.showScore = showScore;
    }

    public void setEpisodesWatched(int episodesWatched) {
        this.episodesWatched = episodesWatched;
    }

    public AnimeItem convertToAnimeItem() {
        AnimeItem tempAnimeItem = new AnimeItem();
        tempAnimeItem.id = this.id;
        tempAnimeItem.synopsis = this.synopsis;
        tempAnimeItem.link = this.link ;
        tempAnimeItem.averageRating = this.averageRating;
        tempAnimeItem.popularityRank = this.popularityRank;
        tempAnimeItem.ratingRank = this.ratingRank;
        tempAnimeItem.showType = this.showType;
        tempAnimeItem.status = this.status;
        tempAnimeItem.episodeCount = this.episodeCount ;
        tempAnimeItem.episodeLength = this.episodeLength;
        tempAnimeItem.youtubeVideoId = this.youtubeVideoId;
        tempAnimeItem.title = this.title;          //english title
        tempAnimeItem.en_jp = this.en_jp;       //japanese title in en
        tempAnimeItem.ja_jp = this.ja_jp;       //japanese title in jp
        tempAnimeItem.tiny = this.tiny;

        return tempAnimeItem;
    }
}
