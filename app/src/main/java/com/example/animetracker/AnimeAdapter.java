package com.example.animetracker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
//import com.example.android.animetracker.data.AnimeItem;
//import com.example.android.animetracker.data.AnimePreferences;
//import com.example.android.animetracker.utils.OpenWeatherMapUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;



public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeItemViewHolder> {

    private List<AnimeItem> mAnimeItems;
    private OnAnimeItemClickListener mAnimeItemClickListener;

    public interface OnAnimeItemClickListener {
        void onAnimeItemClick(AnimeItem animeItem);
    }

    public AnimeAdapter(OnAnimeItemClickListener clickListener) {
        mAnimeItemClickListener = clickListener;
    }

    public void updateAnimeItems(List<AnimeItem> animeItems) {
        mAnimeItems = animeItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAnimeItems != null) {
            return mAnimeItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public AnimeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.anime_list_item, parent, false);
        return new AnimeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimeItemViewHolder holder, int position) {
        holder.bind(mAnimeItems.get(position));
    }

    class AnimeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mAnimeTitleTV;
        //private TextView mAnime_TV;
        private ImageView mAnimeIconIV;

        public AnimeItemViewHolder(View itemView) {
            super(itemView);
            mAnimeTitleTV = itemView.findViewById(R.id.tv_title);
            //mAnime_TV = itemView.findViewById(R.id.tv_anime_);
            mAnimeIconIV = itemView.findViewById(R.id.iv_poster_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeItem animeItem) {
            String titleString = DateFormat.getDateTimeInstance().format(animeItem.title);
            /*String detailString = mAnimeTempDescriptionTV.getContext().getString(
                    R.string.anime_item_details, animeItem.temperature,
                    WeatherPreferences.getDefaultTemperatureUnitsAbbr(), animeItem.description
            );*/

            //builds the anime url here
            //String iconURL = OpenWeatherMapUtils.buildIconURL(animeItem.icon);
            mAnimeTitleTV.setText(titleString);
            //mAnimeDescriptionTV.setText(detailString);

            //uses glide to display the image
            //Glide.with(mWeatherIconIV.getContext()).load(iconURL).into(mWeatherIconIV);
        }

        @Override
        public void onClick(View v) {
            AnimeItem animeItem = mAnimeItems.get(getAdapterPosition());
            mAnimeItemClickListener.onAnimeItemClick(animeItem);
        }
    }
}