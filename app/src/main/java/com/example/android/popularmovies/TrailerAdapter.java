package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        LinearLayout linearLayout;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            playImageView = itemView.findViewById(R.id.play);
            trailerTextView = itemView.findViewById(R.id.trailer);
            linearLayout = itemView.findViewById(R.id.trailer_item);
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
    public void onBindViewHolder(TrailerAdapterViewHolder holder, final int position) {
        playImageView.setImageResource(R.drawable.ic_play_arrow_gray_24dp);
        trailerTextView.setText("Trailer " + Integer.toString(position + 1));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mTrailers.get(position)));
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
}
