package com.example.animetracker.ui.slideshow;

import android.content.Intent;
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
import com.example.animetracker.AnimeItemDetailActivity;
import com.example.animetracker.AnimeSearchViewModel;
import com.example.animetracker.R;
import com.example.animetracker.data.AnimeItem;
import com.example.animetracker.data.AnimeSearchPages;
import com.example.animetracker.data.Status;
import com.example.animetracker.ui.gallery.GalleryFragment;

import com.google.android.material.snackbar.Snackbar;

import com.example.animetracker.utils.KitsuUtils;


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

    private AnimeSearchPages mPages;
    private Button loadMoreButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        loadMoreButton = root.findViewById(R.id.btn_genre_load_more);
        mSearchBoxET = root.findViewById(R.id.et_genre_search_box);
        mSearchResultsRV = (RecyclerView) root.findViewById(R.id.rv_search_anime_genre);

        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchResultsRV.setHasFixedSize(true);

        mAdapter = new AnimeAdapter(this);
        mSearchResultsRV.setAdapter(mAdapter);


        mLoadingIndicatorPB = root.findViewById(R.id.pb_loading_genre_indicator);
        mErrorMessageTV = root.findViewById(R.id.tv_genre_error_message);

        //mViewModel = new ViewModelProvider(this).get(AnimeSearchViewModel.class);
        mViewModel = ViewModelProviders.of(getActivity()).get(AnimeSearchViewModel.class);

        mViewModel.getGenreSearchResults().observe(this, new Observer<List<AnimeItem>>() {
            @Override
            public void onChanged(List<AnimeItem> animeItems) {
                mAdapter.updateAnimeItems(animeItems);
            }
        });

        mViewModel.getGenreSearchPages().observe(this, new Observer<AnimeSearchPages>() {
            @Override
            public void onChanged(AnimeSearchPages searchPages) {
                mPages = searchPages;
            }
        });

        mViewModel.getGenreSearchLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status == Status.LOADING){
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                    loadMoreButton.setVisibility(View.INVISIBLE);
                } else if(status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.VISIBLE);
                    mErrorMessageTV.setVisibility(View.INVISIBLE);
                    loadMoreButton.setVisibility(View.VISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.INVISIBLE);
                    mErrorMessageTV.setVisibility(View.VISIBLE);
                    loadMoreButton.setVisibility(View.INVISIBLE);
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

        loadMoreButton = root.findViewById(R.id.btn_genre_load_more);
        loadMoreButton.setVisibility(View.INVISIBLE);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Load more anime");
                if(mPages.next != null) {
                    doAnimeGenreLoadMore(mPages.next);
                } else{
                    Snackbar.make(v, "No more results", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        return root;
    }

    @Override
    public void onAnimeItemClick(AnimeItem anime){

        Intent intent = new Intent(getActivity(), AnimeItemDetailActivity.class);
        intent.putExtra(KitsuUtils.EXTRA_ANIME_ITEM, anime);
        startActivity(intent);
        Log.d(TAG, "this does something");
    }

    private void doAnimeGenreSearch(String animeGenre){
        mViewModel.loadGenreSearchResults(animeGenre);
    }

    private void doAnimeGenreLoadMore(String nextPageUrl){
        mViewModel.loadNextGenrePageResults(nextPageUrl);
    }
}
