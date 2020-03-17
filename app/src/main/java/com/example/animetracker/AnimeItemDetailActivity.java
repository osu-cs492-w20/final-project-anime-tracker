package com.example.animetracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

//import com.bumptech.glide.Glide;
//import com.example.android.animetracker.data.WeatherPreferences;
import com.bumptech.glide.Glide;
import com.example.animetracker.data.AnimeDatabaseEntry;
import com.example.animetracker.data.AnimeItem;
import com.example.animetracker.utils.KitsuUtils;

import java.text.DateFormat;
import java.util.List;

public class AnimeItemDetailActivity extends AppCompatActivity{


    private ImageView mPosterIconIV;
    private TextView mSynopsisTV;
    private TextView mTitleTV;
    private TextView mAverageRatingTV;
    private TextView mPopularRankTV;
    private TextView mShowTypeTV;
    private TextView mStatusTV;
    private TextView mEpisodeCountTV;
    private TextView mEpisodeLengthTV;
    private TextView mYoutubeIDTV;

    private TextView mDatabaseScoreTV;
    private TextView mDatabaseWatchedTV;

    private AnimeItem mAnimeItem;

    private AnimeListViewModel mAnimeListViewModel;
    private boolean mIsSaved = false;
    private AnimeDatabaseEntry mAnimeDatabaseEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_item_detail);


        mPosterIconIV = findViewById(R.id.iv_poster_icon);

        mSynopsisTV = findViewById(R.id.tv_synopsis);
        mTitleTV = findViewById(R.id.tv_title);
        mAverageRatingTV = findViewById(R.id.tv_avg_rating);
        mPopularRankTV = findViewById(R.id.tv_popularity_rank);
        mShowTypeTV = findViewById(R.id.tv_show_type);
        mStatusTV = findViewById(R.id.tv_status);
        mEpisodeCountTV = findViewById(R.id.tv_episode_count);
        mEpisodeLengthTV = findViewById(R.id.tv_episode_length);
        mYoutubeIDTV = findViewById(R.id.tv_youtube_id);

        mDatabaseScoreTV = findViewById(R.id.tv_database_score);
        mDatabaseWatchedTV = findViewById(R.id.tv_database_watched);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(KitsuUtils.EXTRA_ANIME_ITEM)) {
            mAnimeItem = (AnimeItem)intent.getSerializableExtra(
                    KitsuUtils.EXTRA_ANIME_ITEM
            );
            fillInLayout(mAnimeItem);
        }


        Button goButton = findViewById(R.id.btn_link);
        goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mAnimeItem.id != null) {
                    Uri kitsuUri = Uri.parse("https://kitsu.io/anime/" + mAnimeItem.id);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, kitsuUri);

                    PackageManager pm = getPackageManager();
                    List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (activities.size() > 0) {
                        startActivity(webIntent);
                    }
                }
            }
        });

        Button youtubeButton = findViewById(R.id.btn_youtube);
        if (mAnimeItem.youtubeVideoId == null || mAnimeItem.youtubeVideoId == "") {
            youtubeButton.setVisibility(View.INVISIBLE);
        } else {
            youtubeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                if (mAnimeItem.youtubeVideoId != null && mAnimeItem.youtubeVideoId != "") {
                    Uri youtubeUri = Uri.parse("https://youtu.be/" + mAnimeItem.youtubeVideoId);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, youtubeUri);

                    PackageManager pm = getPackageManager();
                    List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (activities.size() > 0) {
                        startActivity(webIntent);
                    }
                }
                }
            });
        }

        mAnimeListViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(AnimeListViewModel.class);

        final Button AddAnimeDatabaseButton = findViewById(R.id.btn_add_to_list);
        AddAnimeDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimeDatabaseEntry tempAnimeDatabaseEntry = new AnimeDatabaseEntry();
                tempAnimeDatabaseEntry.setAnimeDatabaseEntry(mAnimeItem);
                if (!mIsSaved) {
                    mAnimeListViewModel.insertAnimeListEntry(tempAnimeDatabaseEntry);
                } else {
                    mAnimeListViewModel.deleteAnimeListEntry(tempAnimeDatabaseEntry);
                }
            }
        });

        mAnimeDatabaseEntry = null;
        mAnimeListViewModel.getAnimeListEntryByName(mAnimeItem.id).observe(this, new Observer<AnimeDatabaseEntry>() {
            @Override
            public void onChanged(AnimeDatabaseEntry animeDatabaseEntry) {
                if (animeDatabaseEntry != null) {
                    mIsSaved = true;
                    mAnimeDatabaseEntry = animeDatabaseEntry;
                    AddAnimeDatabaseButton.setText("Remove from your list");
                } else {
                    mIsSaved = false;
                    AddAnimeDatabaseButton.setText("Add to your list");
                }
            }
        });
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.anime_item_detail, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_share:
                shareAnime();
                return true;

             */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* public void shareAnime() {
        if (mAnimeItem != null) {
            String dateString = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                    .format(mAnimeItem.dateTime);
            String shareText = getString(R.string.anime_item_share_text,
                    WeatherPreferences.getDefaultAnimeLocation(), dateString,
                    mAnimeItem.temperature, WeatherPreferences.getDefaultTemperatureUnitsAbbr(),
                    mAnimeItem.description);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        }
    }
    */
    private void fillInLayout(AnimeItem animeItem) {
        // anime titles
        //public String title;          //english title
        //    public String en_jp;       //japanese title in en
        //    public String ja_jp;       //japanese title in jp

        if (animeItem.title != null){
            String animeTitleString = getString(R.string.anime_title, animeItem.title);
            mTitleTV.setText(animeTitleString);
        }
        else if (animeItem.en_jp != null){
            String animeTitleString = getString(R.string.anime_title, animeItem.en_jp);
            mTitleTV.setText(animeTitleString);
        }
        else if (animeItem.ja_jp != null){
            String animeTitleString = getString(R.string.anime_title, animeItem.ja_jp);
            mTitleTV.setText(animeTitleString);
        }
        else
            mTitleTV.setText(getString(R.string.no_anime_title));


        Glide.with(this).load(animeItem.tiny).into(mPosterIconIV);

        String animeAverageRatingString = getString(R.string.anime_avg_rating, animeItem.averageRating);
        mAverageRatingTV.setText(animeAverageRatingString);

        String animePopularRankString = getString(R.string.anime_popular_rank, String.valueOf(animeItem.popularityRank));
        mPopularRankTV.setText(animePopularRankString);

        String animeShowTypeString = getString(R.string.anime_show_type, animeItem.showType);
        mShowTypeTV.setText(animeShowTypeString);

        String animeStatusString = getString(R.string.anime_status, animeItem.status);
        mStatusTV.setText(animeStatusString);

        String animeEpisodeCountString = getString(R.string.anime_episode_count, String.valueOf(animeItem.episodeCount));
        mEpisodeCountTV.setText(animeEpisodeCountString);

        String animeEpisodeLengthString = getString(R.string.anime_episode_length, String.valueOf(animeItem.episodeLength));
        mEpisodeLengthTV.setText(animeEpisodeLengthString);

        String animeYoutubeString = getString(R.string.anime_youtube_id, animeItem.youtubeVideoId);
        mYoutubeIDTV.setText(animeYoutubeString);

        String animeSynopsisString = getString(R.string.anime_synopsis, animeItem.synopsis);
        mSynopsisTV.setText(animeSynopsisString);

        String animeScoreString = getString(R.string.anime_score); //, String.valueOf(animeItem.));
        mDatabaseScoreTV.setText(animeScoreString);

        String animeWatchedString = getString(R.string.anime_watched); //, String.valueOf(animeItem.));
        mDatabaseWatchedTV.setText(animeWatchedString);



    }
}
