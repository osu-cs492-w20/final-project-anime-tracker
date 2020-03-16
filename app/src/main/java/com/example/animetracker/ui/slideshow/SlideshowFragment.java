package com.example.animetracker.ui.slideshow;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animetracker.AnimeAdapter;
import com.example.animetracker.AnimeSearchViewModel;
import com.example.animetracker.R;
import com.example.animetracker.data.AnimeItem;
import com.example.animetracker.data.Status;
import com.example.animetracker.ui.gallery.GalleryFragment;

import java.util.List;

public class SlideshowFragment extends Fragment implements AnimeAdapter.OnAnimeItemClickListener{
    private static final String TAG = SlideshowFragment.class.getSimpleName();

    private SlideshowViewModel slideshowViewModel;

    private AnimeSearchViewModel mViewModel;
    private AnimeAdapter mAdapter;

    private RecyclerView mSearchResultsRV;
    private EditText mSearchBoxET;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        mSearchBoxET = root.findViewById(R.id.et_genre_search_box);
        mSearchResultsRV = (RecyclerView) root.findViewById(R.id.rv_search_anime_genre);

        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchResultsRV.setHasFixedSize(true);

        mAdapter = new AnimeAdapter(this);
        mSearchResultsRV.setAdapter(mAdapter);


        mLoadingIndicatorPB = root.findViewById(R.id.pb_loading_genre_indicator);
        mErrorMessageTV = root.findViewById(R.id.tv_genre_error_message);

        //mViewModel = new ViewModelProvider(this).get(AnimeSearchViewModel.class);
        mViewModel = ViewModelProviders.of(this).get(AnimeSearchViewModel.class);

        mViewModel.getGenreSearchResults().observe(this, new Observer<List<AnimeItem>>() {
            @Override
            public void onChanged(List<AnimeItem> animeItems) {
                mAdapter.updateAnimeItems(animeItems);
            }
        });

        mViewModel.getGenreSearchLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status == Status.LOADING){
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if(status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.VISIBLE);
                    mErrorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.INVISIBLE);
                    mErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });

        Button searchButton = root.findViewById(R.id.btn_genre_search);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String searchQuery = mSearchBoxET.getText().toString();
                if(!TextUtils.isEmpty(searchQuery)){
                    doAnimeGenreSearch(searchQuery);
                }
            }
        });


        return root;
    }

    @Override
    public void onAnimeItemClick(AnimeItem anime){
        Log.d(TAG, "this does nothing");
    }

    private void doAnimeGenreSearch(String animeGenre){
        mViewModel.loadGenreSearchResults(animeGenre);
    }
}
