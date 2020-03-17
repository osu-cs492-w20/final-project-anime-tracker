package com.example.animetracker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animetracker.data.AnimeDatabaseEntry;

import java.text.DateFormat;
import java.util.List;

//import com.bumptech.glide.Glide;
//import com.example.android.animetracker.data.AnimePreferences;
//import com.example.android.animetracker.utils.OpenWeatherMapUtils;
import com.example.animetracker.data.AnimeDatabaseEntry;

public class AnimeDatabaseAdapter extends RecyclerView.Adapter<AnimeDatabaseAdapter.AnimeDatabaseEntryViewHolder> {

    private List<AnimeDatabaseEntry> mAnimeDatabaseEntries;
    private OnAnimeDatabaseEntryClickListener mAnimeDatabaseEntryClickListener;

    public interface OnAnimeDatabaseEntryClickListener {
        void onAnimeDatabaseEntryClick(AnimeDatabaseEntry animeDatabaseEntry);
    }

    public AnimeDatabaseAdapter(OnAnimeDatabaseEntryClickListener clickListener) {
        mAnimeDatabaseEntryClickListener = clickListener;
    }

    public void updateAnimeDatabaseEntries(List<AnimeDatabaseEntry> animeDatabaseEntries) {
        mAnimeDatabaseEntries = animeDatabaseEntries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAnimeDatabaseEntries != null) {
            return mAnimeDatabaseEntries.size();
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
        holder.bind(mAnimeDatabaseEntries.get(position));
    }

    class AnimeDatabaseEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mAnimeTitleTV;
        private TextView mAnimeScoreTV;
        private TextView mAnimeEpisodesWatchedTV;
        private ImageView mAnimeIconIV;

        public AnimeDatabaseEntryViewHolder(View itemView) {
            super(itemView);
            mAnimeTitleTV = itemView.findViewById(R.id.tv_title);
            mAnimeScoreTV = itemView.findViewById(R.id.tv_show_score);
            mAnimeEpisodesWatchedTV = itemView.findViewById(R.id.tv_episodes_watched);
            mAnimeIconIV = itemView.findViewById(R.id.iv_poster_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeDatabaseEntry animeDatabaseEntry) {
            String titleString = null;
            if(animeDatabaseEntry.title != null){
                titleString = animeDatabaseEntry.title;
            } else if (animeDatabaseEntry.en_jp != null) {
                titleString = animeDatabaseEntry.en_jp;
            } else if (animeDatabaseEntry.ja_jp != null) {
                titleString = animeDatabaseEntry.ja_jp;
            } else {
                titleString = "No Title Available";
            }
            mAnimeTitleTV.setText(titleString);

            String animeScoreString = "Your Score: " + animeDatabaseEntry.showScore;
            mAnimeScoreTV.setText(animeScoreString);
            mAnimeScoreTV.setVisibility(View.VISIBLE);

            String animeWatchedString = "Current Episodes Watched: " + animeDatabaseEntry.episodesWatched;
            mAnimeEpisodesWatchedTV.setText(animeWatchedString);
            mAnimeEpisodesWatchedTV.setVisibility(View.VISIBLE);

            String iconURL = animeDatabaseEntry.tiny;
            //uses glide to display the image
            Glide.with(mAnimeIconIV.getContext()).load(iconURL).into(mAnimeIconIV);
        }

        @Override
        public void onClick(View v) {
            AnimeDatabaseEntry animeDatabaseEntry = mAnimeDatabaseEntries.get(getAdapterPosition());
            mAnimeDatabaseEntryClickListener.onAnimeDatabaseEntryClick(animeDatabaseEntry);
        }
    }
}