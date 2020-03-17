package com.example.animetracker.ui.tools;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.animetracker.AnimeListViewModel;
import com.example.animetracker.R;
import com.example.animetracker.data.AnimeDatabaseEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

public class ToolsFragment extends Fragment {

    //private ToolsViewModel toolsViewModel;
    private TextView animeExportJsonTV;
    private AnimeListViewModel animeListViewModel;
    private List<AnimeDatabaseEntry> animeDatabaseEntriesExport;
    private Toast mTodoToast;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        toolsViewModel =
//                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
//        final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mTodoToast = null;
        animeExportJsonTV = root.findViewById(R.id.tv_export_anime_json);

        animeListViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
        ).get(AnimeListViewModel.class);

        animeListViewModel.getAllAnimeListEntries().observe(this, new Observer<List<AnimeDatabaseEntry>>() {
            @Override
            public void onChanged(List<AnimeDatabaseEntry> animeDatabaseEntries) {
                animeDatabaseEntriesExport = animeDatabaseEntries;
            }
        });

        Button exportButton = root.findViewById(R.id.btn_export_anime_list);
        exportButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = getActivity().getApplicationContext();
                String json = generateJson(animeDatabaseEntriesExport);
                Log.d("Export JSON Fragment", "exported JSON: " + json);
                writeToFile(json, context);
                animeExportJsonTV.setText(json);
                if (mTodoToast!= null) {
                    mTodoToast.cancel();
                }
                String toastText = "Finished Exporting AnimeListExport.json";
                mTodoToast = Toast.makeText(getActivity().getApplicationContext(), toastText, Toast.LENGTH_LONG);
                mTodoToast.show();
            }
        });

        return root;
    }

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