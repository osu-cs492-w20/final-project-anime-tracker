package com.example.animetracker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animetracker.data.AnimeDatabaseEntry;

import java.text.DateFormat;
import java.util.List;

//import com.bumptech.glide.Glide;
//import com.example.android.animetracker.data.AnimePreferences;
//import com.example.android.animetracker.utils.OpenWeatherMapUtils;
import com.example.animetracker.data.AnimeDatabaseEntry;

public class AnimeDatabaseAdapter extends RecyclerView.Adapter<AnimeDatabaseAdapter.AnimeDatabaseEntryViewHolder> {

    private List<AnimeDatabaseEntry> mAnimeDatabaseEntrys;
    private OnAnimeDatabaseEntryClickListener mAnimeDatabaseEntryClickListener;

    public interface OnAnimeDatabaseEntryClickListener {
        void onAnimeDatabaseEntryClick(AnimeDatabaseEntry AnimeDatabaseEntry);
    }

    public AnimeDatabaseAdapter(OnAnimeDatabaseEntryClickListener clickListener) {
        mAnimeDatabaseEntryClickListener = clickListener;
    }

    public void updateAnimeDatabaseEntrys(List<AnimeDatabaseEntry> AnimeDatabaseEntrys) {
        mAnimeDatabaseEntrys = AnimeDatabaseEntrys;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAnimeDatabaseEntrys != null) {
            return mAnimeDatabaseEntrys.size();
        } else {
            return 0;
        }
    }

    @Override
    public AnimeDatabaseEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.anime_list_item, parent, false);
        return new AnimeDatabaseEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimeDatabaseEntryViewHolder holder, int position) {
        holder.bind(mAnimeDatabaseEntrys.get(position));
    }

    class AnimeDatabaseEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mAnimeTitleTV;
        //private TextView mAnime_TV;
        private ImageView mAnimeIconIV;

        public AnimeDatabaseEntryViewHolder(View itemView) {
            super(itemView);
            mAnimeTitleTV = itemView.findViewById(R.id.tv_title);
            //mAnime_TV = itemView.findViewById(R.id.tv_anime_);
            mAnimeIconIV = itemView.findViewById(R.id.iv_poster_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeDatabaseEntry AnimeDatabaseEntry) {
            String titleString = DateFormat.getDateTimeInstance().format(AnimeDatabaseEntry.title);
            /*String detailString = mAnimeTempDescriptionTV.getContext().getString(
                    R.string.anime_item_details, AnimeDatabaseEntry.temperature,
                    WeatherPreferences.getDefaultTemperatureUnitsAbbr(), AnimeDatabaseEntry.description
            );*/

            //builds the anime url here
            //String iconURL = OpenWeatherMapUtils.buildIconURL(AnimeDatabaseEntry.icon);
            mAnimeTitleTV.setText(titleString);
            //mAnimeDescriptionTV.setText(detailString);

            //uses glide to display the image
            //Glide.with(mWeatherIconIV.getContext()).load(iconURL).into(mWeatherIconIV);
        }

        @Override
        public void onClick(View v) {
            AnimeDatabaseEntry AnimeDatabaseEntry = mAnimeDatabaseEntrys.get(getAdapterPosition());
            mAnimeDatabaseEntryClickListener.onAnimeDatabaseEntryClick(AnimeDatabaseEntry);
        }
    }
}