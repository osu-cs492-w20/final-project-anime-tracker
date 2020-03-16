package com.example.animetracker.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animetracker.utils.KitsuUtils;

import java.util.List;

public class KitsuSearchGenreRepository implements KitsuSearchAsyncTask.Callback {
    private static final String TAG = KitsuSearchGenreRepository.class.getSimpleName();
    private MutableLiveData<List<AnimeItem>> mSearhGenreResults;

    private MutableLiveData<Status> mLoadingGenreStatus;



    public KitsuSearchGenreRepository() {
        mSearhGenreResults = new MutableLiveData<>();
        mSearhGenreResults.setValue(null);

        mLoadingGenreStatus = new MutableLiveData<>();
        mLoadingGenreStatus.setValue(Status.SUCCESS);
    }


    public LiveData<List<AnimeItem>> getSearchGenreResults() {
        return mSearhGenreResults;
    }



    public LiveData<Status> getLoadingGenreStatus () {
        return mLoadingGenreStatus;
    }

    @Override
    public void onSearchFinished(List<AnimeItem> searchResults){
        mSearhGenreResults.setValue(searchResults);
        if (searchResults !=null){
            mLoadingGenreStatus.setValue(Status.SUCCESS);
        } else{
            mLoadingGenreStatus.setValue(Status.ERROR);
        }
    }


    public void loadGenreSearch(String genre){
        mSearhGenreResults.setValue(null);
        mLoadingGenreStatus.setValue(Status.LOADING);
        String url = KitsuUtils.buildKitsuSearchGenre(genre);
        Log.d(TAG, "fetching new search by title data with this URL: " + url);
        new KitsuSearchAsyncTask(this).execute(url);
    }
}
