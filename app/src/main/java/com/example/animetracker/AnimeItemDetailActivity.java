package com.example.animetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.bumptech.glide.Glide;
//import com.example.android.animetracker.data.WeatherPreferences;
import com.example.animetracker.data.AnimeItem;
import com.example.animetracker.utils.KitsuUtils;

import java.text.DateFormat;

public class AnimeItemDetailActivity extends AppCompatActivity {


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

    private AnimeItem mAnimeItem;

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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(KitsuUtils.EXTRA_ANIME_ITEM)) {
            mAnimeItem = (AnimeItem)intent.getSerializableExtra(
                    KitsuUtils.EXTRA_ANIME_ITEM
            );
            fillInLayout(mAnimeItem);
        }

        Button addButton = findViewById(R.id.btn_add_to_list);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

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
        /*String dateString = DateFormat.getDateTimeInstance().format(animeItem.dateTime);
        String detailString = getString(R.string.anime_item_details, animeItem.temperature,
                WeatherPreferences.getDefaultTemperatureUnitsAbbr(), animeItem.description);
        String lowHighTempString = getString(R.string.anime_item_low_high_temp,
                animeItem.temperatureLow, animeItem.temperatureHigh,
                WeatherPreferences.getDefaultTemperatureUnitsAbbr());
        String windString = getString(R.string.anime_item_wind, animeItem.windSpeed,
                animeItem.windDirection);
        String humidityString = getString(R.string.anime_item_humidity, animeItem.humidity);
        String iconURL = OpenWeatherMapUtils.buildIconURL(animeItem.icon);


         */


        //Glide.with(this).load(iconURL).into(mPosterIconIV);
        mSynopsisTV.setText(animeItem.synopsis);
        mTitleTV.setText(animeItem.title);
        mAverageRatingTV.setText(animeItem.averageRating);
        mPopularRankTV.setText(animeItem.popularityRank);
        mShowTypeTV.setText(animeItem.showType);
        mStatusTV.setText(animeItem.status);
        mEpisodeCountTV.setText(animeItem.episodeCount);
        mEpisodeLengthTV.setText(animeItem.episodeLength);
        mYoutubeIDTV.setText(animeItem.youtubeVideoId);

    }
}
