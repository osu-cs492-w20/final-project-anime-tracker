package com.example.animetracker.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animetracker.AnimeDatabaseAdapter;
import com.example.animetracker.AnimeItemDetailActivity;
import com.example.animetracker.AnimeListViewModel;
import com.example.animetracker.R;
import com.example.animetracker.data.AnimeDatabaseEntry;
import com.example.animetracker.utils.KitsuUtils;

import java.util.List;

public class HomeFragment extends Fragment implements AnimeDatabaseAdapter.OnAnimeDatabaseEntryClickListener{


    private static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView mAnimeItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private HomeViewModel mHomeViewModel;

    private AnimeListViewModel mViewModel;
    private AnimeDatabaseAdapter mAdapter;
    private TextView mAnimeListMessageTV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
//
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//
//        final TextView textView = root.findViewById(R.id.tv_anime_list_message);
//
//        mHomeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mAnimeListMessageTV = root.findViewById(R.id.tv_anime_list_message);
        mAnimeItemsRV = (RecyclerView) root.findViewById(R.id.rv_anime_items);

        mAnimeItemsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAnimeItemsRV.setHasFixedSize(true);

        mAdapter = new AnimeDatabaseAdapter(this);
        mAnimeItemsRV.setAdapter(mAdapter);


        mLoadingIndicatorPB = root.findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = root.findViewById(R.id.tv_loading_error_message);

        //mViewModel = new ViewModelProvider(this).get(AnimeSearchViewModel.class);
        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
        ).get(AnimeListViewModel.class);

        mViewModel.getAllAnimeListEntries().observe(this, new Observer<List<AnimeDatabaseEntry>>() {
            @Override
            public void onChanged(List<AnimeDatabaseEntry> databaseEntries) {
                mAdapter.updateAnimeDatabaseEntrys(databaseEntries);
            }
        });
        return root;
    }

    @Override
    public void onAnimeDatabaseEntryClick(AnimeDatabaseEntry entry){

        Intent intent = new Intent(getActivity(), AnimeItemDetailActivity.class);
        intent.putExtra(KitsuUtils.EXTRA_ANIME_ITEM, entry);
        startActivity(intent);

        Log.d(TAG, "this does something");
    }

    private void doAnimeListGet(){
        mViewModel.getAllAnimeListEntries();
    }
}