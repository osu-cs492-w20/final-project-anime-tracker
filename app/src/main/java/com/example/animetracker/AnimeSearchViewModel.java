package com.example.animetracker;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.animetracker.data.AnimeItem;
import com.example.animetracker.data.KitsuSearchGenreRepository;
import com.example.animetracker.data.KitsuSearchTitleRepository;
import com.example.animetracker.data.Status;

import java.util.List;

public class AnimeSearchViewModel extends ViewModel {
    private KitsuSearchGenreRepository mSearchGenreRepository;
    private KitsuSearchTitleRepository mSearchTitleRepository;

    private LiveData<List<AnimeItem>> mTitleSearchResults;
    private LiveData<List<AnimeItem>> mGenreSearchResults;

    private LiveData<Status> mTitleSearchLoadingStatus;
    private LiveData<Status> mGenreSearchLoadingStatus;

    public AnimeSearchViewModel() {
        mSearchTitleRepository = new KitsuSearchTitleRepository();
        mSearchGenreRepository = new KitsuSearchGenreRepository();

        mTitleSearchResults = mSearchTitleRepository.getSearchTitleResults();
        mGenreSearchResults = mSearchGenreRepository.getSearchGenreResults();

        mTitleSearchLoadingStatus = mSearchTitleRepository.getLoadingTitleStatus();
        mGenreSearchLoadingStatus = mSearchGenreRepository.getLoadingGenreStatus();
    }

    public void loadTitleSearchResults(String title){
        mSearchTitleRepository.loadTitleSearch(title);
    }

    public void loadGenreSearchResults(String genre){
        mSearchGenreRepository.loadGenreSearch(genre);
    }

    public LiveData<List<AnimeItem>> getTitleSearchResults(){
        return mTitleSearchResults;
    }

    public LiveData<List<AnimeItem>> getGenreSearchResults (){
        return mGenreSearchResults;
    }

    public LiveData<Status> getTitleSEarchLoadingStatus(){
        return mTitleSearchLoadingStatus;
    }

    public LiveData<Status> getGenreSearchLoadingStatus(){
        return mGenreSearchLoadingStatus;
    }
}
