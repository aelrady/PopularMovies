package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private ArrayList<Review> reviews;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ArrayList<String> authorList;
    private ArrayList<String> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        setTitle("Reviews");

        populateReviewActivity();
    }

    private void populateReviewActivity() {
        Intent intent = getIntent();
        reviews = intent.getParcelableArrayListExtra("reviews");

        authorList = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            authorList.add(reviews.get(i).getAuthor());
        }

        reviewList = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            reviewList.add(reviews.get(i).getContent());
        }

        recyclerView = findViewById(R.id.rv_reviews);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        reviewAdapter = new ReviewAdapter(ReviewActivity.this, authorList, reviewList);
        recyclerView.setAdapter(reviewAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(ReviewActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
    }
}
