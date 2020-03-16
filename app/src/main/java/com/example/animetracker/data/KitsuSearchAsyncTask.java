package com.example.animetracker.data;

import android.os.AsyncTask;
import android.telecom.Call;

import com.example.animetracker.utils.KitsuUtils;
import com.example.animetracker.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.Callback;

public class KitsuSearchAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;

    public interface Callback {
        void onSearchFinished(List<AnimeItem> searchResults);

    }

    public KitsuSearchAsyncTask(Callback callback){
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings){
        String url = strings[0];
        String searchResults = null;
        try {
            searchResults = NetworkUtils.doHTTPGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(String s){
        List<AnimeItem> searchResults = null;
        if(s != null){
            searchResults = KitsuUtils.parseKitsuJSON(s);
        }
        mCallback.onSearchFinished(searchResults);
    }

}
