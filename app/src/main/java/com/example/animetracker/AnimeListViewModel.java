package com.example.animetracker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animetracker.data.AnimeDatabaseEntry;
import com.example.animetracker.data.AnimeItem;
import com.example.animetracker.data.AnimeListRepository;
import com.example.animetracker.data.Status;

import java.util.List;

public class AnimeListViewModel extends AndroidViewModel {
    private AnimeListRepository mRepository;


    public AnimeListViewModel(Application application) {
        super(application);
        mRepository = new AnimeListRepository(application);
    }

    public void insertAnimeListEntry(AnimeDatabaseEntry entry) {
        mRepository.insertAnimeEntry(entry);
    }

    public void deleteAnimeListEntry(AnimeDatabaseEntry entry) {
        mRepository.deleteAnimeEntry(entry);
    }

    public void updateAnimeListEntry(AnimeDatabaseEntry entry) {
        mRepository.updateAnimeEntry(entry);
    }

    public LiveData<List<AnimeDatabaseEntry>> getAllAnimeListEntries() {
        return mRepository.getAllAnimeListEntries();
    }

    public LiveData<AnimeDatabaseEntry> getAnimeListEntryByName(String id) {
        return mRepository.getAnimeListEntryByName(id);
    }

}