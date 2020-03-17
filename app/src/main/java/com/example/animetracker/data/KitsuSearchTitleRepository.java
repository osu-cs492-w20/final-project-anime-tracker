package com.example.animetracker.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animetracker.utils.KitsuUtils;

import java.util.List;

public class KitsuSearchTitleRepository implements KitsuSearchAsyncTask.Callback {
    private static final String TAG = KitsuSearchTitleRepository.class.getSimpleName();

    private MutableLiveData<List<AnimeItem>> mSearhTitleResults;
    private MutableLiveData<Status> mLoadingTitleStatus;

    private MutableLiveData<AnimeSearchPages> mPages;




    public KitsuSearchTitleRepository() {

        mSearhTitleResults = new MutableLiveData<>();
        mSearhTitleResults.setValue(null);
        mLoadingTitleStatus = new MutableLiveData<>();
        mLoadingTitleStatus.setValue(Status.SUCCESS);

        mPages = new MutableLiveData<>();
        mPages.setValue(null);

    }

    public LiveData<List<AnimeItem>> getSearchTitleResults() {
        return mSearhTitleResults;
    }


    public LiveData<Status> getLoadingTitleStatus () {
        return mLoadingTitleStatus;
    }

    public LiveData<AnimeSearchPages> getSearchPages() {
        return mPages;
    }



    @Override
    public void onSearchFinished(List<AnimeItem> searchResults, AnimeSearchPages pages){
        mSearhTitleResults.setValue(searchResults);
        mPages.setValue(pages);
        if (searchResults !=null){
            mLoadingTitleStatus.setValue(Status.SUCCESS);
        } else{
            mLoadingTitleStatus.setValue(Status.ERROR);
        }
    }

    public void loadTitleSearch(String title){
        mSearhTitleResults.setValue(null);
        mPages.setValue(null);
        mLoadingTitleStatus.setValue(Status.LOADING);
        String url = KitsuUtils.buildKitsuSearchTitle(title);
        Log.d(TAG, "fetching new search by title data with this URL: " + url);
        new KitsuSearchAsyncTask(this).execute(url);

    }

    public void loadTitlePageSearch(String url){
        new KitsuSearchAsyncTask(this).execute(url);
    }

}
