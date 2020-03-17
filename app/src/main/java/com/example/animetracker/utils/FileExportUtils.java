package com.example.animetracker.utils;

import android.content.Context;
import android.util.Log;

import com.example.animetracker.data.AnimeDatabaseEntry;
import com.example.animetracker.data.AppDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileExportUtils {

    public String generateJson (List<AnimeDatabaseEntry> animeDatabaseEntries) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<AnimeDatabaseEntry>>(){}.getType();
        String json = gson.toJson(animeDatabaseEntries, type);
        return json;
    }

    public void writeToFile (String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("AnimeListExport.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
