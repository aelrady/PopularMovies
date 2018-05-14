package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> mMovies;
    private ArrayList<String> mMoviePosters;
    private Context mContext;

    public MovieAdapter(Context context, ArrayList<String> moviePosters, ArrayList<Movie> movies) {
        this.mContext = context;
        this.mMoviePosters = moviePosters;
        this.mMovies = movies;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mMovieImageView;
        RelativeLayout relativeLayout;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.poster_image);
            relativeLayout = itemView.findViewById(R.id.grid_layout);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, final int position) {
        String moviePoster = mMoviePosters.get(position);
        Picasso.with(mContext).load(moviePoster).into(holder.mMovieImageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("movie", mMovies.get(position));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMoviePosters.size();
    }
}
