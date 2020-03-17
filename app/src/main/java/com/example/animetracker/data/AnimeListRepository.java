package com.example.animetracker.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AnimeListRepository {
    private AnimeListDao mDAO;

    public AnimeListRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.animeListDao();
    }

    public void insertAnimeEntry(AnimeDatabaseEntry entry) {
        new InsertAsyncTask(mDAO).execute(entry);
    }

    public void deleteAnimeEntry(AnimeDatabaseEntry entry) {
        new DeleteAsyncTask(mDAO).execute(entry);
    }

    public void updateAnimeEntry(AnimeDatabaseEntry entry) {
        new UpdateAsyncTask(mDAO).execute(entry);
    }

    public LiveData<List<AnimeDatabaseEntry>> getAllAnimeListEntries() {
        return mDAO.getAllAnimeListEntries();
    }

    public LiveData<AnimeDatabaseEntry> getAnimeListEntryByName (String id) {
        return mDAO.getAnimeListEntryByName(id);
    }

    private static class InsertAsyncTask extends AsyncTask<AnimeDatabaseEntry, Void, Void> {
        private AnimeListDao mAsyncTaskDAO;

        InsertAsyncTask(AnimeListDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(AnimeDatabaseEntry... animeDatabaseEntries) {
            mAsyncTaskDAO.insert(animeDatabaseEntries[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<AnimeDatabaseEntry, Void, Void> {
        private AnimeListDao mAsyncTaskDAO;

        DeleteAsyncTask(AnimeListDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(AnimeDatabaseEntry... animeDatabaseEntries) {
            mAsyncTaskDAO.delete(animeDatabaseEntries[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<AnimeDatabaseEntry, Void, Void> {
        private AnimeListDao mAsyncTaskDAO;

        UpdateAsyncTask(AnimeListDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(AnimeDatabaseEntry... animeDatabaseEntries) {
            mAsyncTaskDAO.update(animeDatabaseEntries[0]);
            return null;
        }
    }



}
