package com.example.animetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.bumptech.glide.Glide;
//import com.example.android.animetracker.data.AnimeItem;
//import com.example.android.animetracker.data.WeatherPreferences;
//import com.example.android.animetracker.utils.OpenWeatherMapUtils;

import java.text.DateFormat;

public class AnimeItemDetailActivity extends AppCompatActivity {

    private TextView mDateTV;
    private TextView mTempDescriptionTV;
    private TextView mLowHighTempTV;
    private TextView mWindTV;
    private TextView mHumidityTV;
    private ImageView mWeatherIconIV;

    private AnimeItem mAnimeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_item_detail);

        mDateTV = findViewById(R.id.tv_date);
        mTempDescriptionTV = findViewById(R.id.tv_temp_description);
        mLowHighTempTV = findViewById(R.id.tv_low_high_temp);
        mWindTV = findViewById(R.id.tv_wind);
        mHumidityTV = findViewById(R.id.tv_humidity);
        mWeatherIconIV = findViewById(R.id.iv_weather_icon);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(OpenWeatherMapUtils.EXTRA_FORECAST_ITEM)) {
            mAnimeItem = (AnimeItem)intent.getSerializableExtra(
                    OpenWeatherMapUtils.EXTRA_FORECAST_ITEM
            );
            fillInLayout(mAnimeItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anime_item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareAnime();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareAnime() {
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

    private void fillInLayout(AnimeItem animeItem) {
        String dateString = DateFormat.getDateTimeInstance().format(animeItem.dateTime);
        String detailString = getString(R.string.anime_item_details, animeItem.temperature,
                WeatherPreferences.getDefaultTemperatureUnitsAbbr(), animeItem.description);
        String lowHighTempString = getString(R.string.anime_item_low_high_temp,
                animeItem.temperatureLow, animeItem.temperatureHigh,
                WeatherPreferences.getDefaultTemperatureUnitsAbbr());

        String windString = getString(R.string.anime_item_wind, animeItem.windSpeed,
                animeItem.windDirection);
        String humidityString = getString(R.string.anime_item_humidity, animeItem.humidity);
        String iconURL = OpenWeatherMapUtils.buildIconURL(animeItem.icon);

        mDateTV.setText(dateString);
        mTempDescriptionTV.setText(detailString);
        mLowHighTempTV.setText(lowHighTempString);
        mWindTV.setText(windString);
        mHumidityTV.setText(humidityString);
        Glide.with(this).load(iconURL).into(mWeatherIconIV);
    }
}
