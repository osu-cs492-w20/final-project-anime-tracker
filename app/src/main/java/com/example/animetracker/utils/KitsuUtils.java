package com.example.animetracker.utils;

import android.net.Uri;

import com.example.animetracker.data.AnimeItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class KitsuUtils {
    private final static String KITSU_ANIME_SEARCH_URL = "https://kitsu.io/api/edge/anime";
    private final static String KITSU_TITLE_QUERY_PARAM = "filter[text]";
    private final static String KITSU_GENRE_QUERY_PARAM = "filter[genres]";




    public static String buildKitsuSearchTitle(String title) {
        return Uri.parse(KITSU_ANIME_SEARCH_URL).buildUpon()
                .appendQueryParameter(KITSU_TITLE_QUERY_PARAM, title)
                .build()
                .toString();
    }

    public static String buildKitsuSearchGenre(String genre){
        return Uri.parse(KITSU_ANIME_SEARCH_URL).buildUpon()
                .appendQueryParameter(KITSU_GENRE_QUERY_PARAM, genre)
                .build()
                .toString();
    }

    public static String buildKitsuGetAnime(String id){
        return Uri.parse(KITSU_ANIME_SEARCH_URL).buildUpon()
                .appendPath(id)
                .build()
                .toString();
    }

    static class KitsuSearchResults {
        KitsuAnimeItem[] list;
    }

    static class KitsuAnimeItem {
        String id;
        KitsuAnimeItemAttributes attributes;
        KitsuSelfLink links;
    }
    static class KitsuSelfLink {
        String self;
    }

    static class KitsuAnimeItemAttributes {
        String synopsis;
        KitsuAnimeItemTitle titles;
        String averageRating;
        int popularityRank;
        int ratingRank;
        String showType;
        String status;
        int episodeCount;
        int episodeLength;
        String youtubeVideoId;
        KitsuAnimeImage posterImage;

    }

    static class KitsuAnimeItemTitle {
        String en;          //english title
        String en_jp;       //japanese title in en
        String ja_jp;       //japanese title in jp
    }

    static class KitsuAnimeImage {
        String tiny;
        String small;
        String medium;
        String large;
    }

    public static ArrayList<AnimeItem> parseKitsuJSON(String kitsuJSON){
        Gson gson = new Gson();
        KitsuSearchResults results =gson.fromJson(kitsuJSON, KitsuSearchResults.class);
        if (results != null && results.list !=null) {
            ArrayList<AnimeItem> animeItems = new ArrayList<>();

            for (KitsuAnimeItem listItem :results.list) {
                AnimeItem animeItem = new AnimeItem();

                animeItem.synopsis = listItem.attributes.synopsis;
                animeItem.id = listItem.id;
                animeItem.link = listItem.links.self;
                animeItem.title = listItem.attributes.titles.en;
                animeItem.averageRating = listItem.attributes.averageRating;
                animeItem.popularityRank = listItem.attributes.popularityRank;
                animeItem.ratingRank = listItem.attributes.ratingRank;
                animeItem.showType = listItem.attributes.showType;
                animeItem.status = listItem.attributes.status;
                animeItem.tiny = listItem.attributes.posterImage.tiny;
                animeItem.episodeCount = listItem.attributes.episodeCount;
                animeItem.episodeLength =listItem.attributes.episodeLength;
                animeItem.youtubeVideoId = listItem.attributes.youtubeVideoId;
                animeItem.ja_jp =listItem.attributes.titles.ja_jp;
                animeItem.en_jp =listItem.attributes.titles.en_jp;
            }
            return animeItems;
        } else {
            return null;
        }
    }


}
