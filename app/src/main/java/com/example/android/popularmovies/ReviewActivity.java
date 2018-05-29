package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private ArrayList<Review> reviews;

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
        Log.v("Show More Reviews: ", String.valueOf(reviews));
    }
}
