package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private ArrayList<String> mTrailers;
    private Context mContext;
    private ImageView playImageView;
    private TextView trailerTextView;

    public TrailerAdapter(Context context, ArrayList<String> trailers) {
        this.mContext = context;
        this.mTrailers = trailers;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder {

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            playImageView = itemView.findViewById(R.id.play);
            trailerTextView = itemView.findViewById(R.id.trailer);
        }
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForReviewItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForReviewItem, viewGroup, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        playImageView.setImageResource(R.drawable.ic_play_arrow_gray_24dp);
        trailerTextView.setText("Trailer " + Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
}
