package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private ArrayList<String> mAuthors;
    private ArrayList<String> mReviews;
    private Context mContext;
    private TextView authorTextView;
    private TextView reviewTextView;

    public ReviewAdapter(Context context, ArrayList<String> authors, ArrayList<String> reviews) {
        this.mContext = context;
        this.mAuthors = authors;
        this.mReviews = reviews;
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.author);
            reviewTextView = itemView.findViewById(R.id.review);
        }
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForReviewItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForReviewItem, viewGroup, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        String author = mAuthors.get(position);
        String review = mReviews.get(position);

        authorTextView.setText(author);
        reviewTextView.setText(review);

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
