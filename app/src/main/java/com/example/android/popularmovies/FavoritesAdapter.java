package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private List<Movie> mMovies;
    private ArrayList<String> mMoviePosters;
    private Context mContext;

    public FavoritesAdapter(Context context, ArrayList<String> moviePosters, List<Movie> movies) {
        this.mContext = context;
        this.mMoviePosters = moviePosters;
        this.mMovies = movies;
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mMovieImageView;
        RelativeLayout relativeLayout;

        public FavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.poster_image);
            relativeLayout = itemView.findViewById(R.id.grid_layout);
        }
    }

    @Override
    public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, false);
        return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, final int position) {
        String moviePoster = mMoviePosters.get(position);
        Picasso.with(mContext).load(moviePoster).into(holder.mMovieImageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FavoritesActivity.class);
                intent.putExtra("movie_id", mMovies.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMoviePosters.size();
    }
}
