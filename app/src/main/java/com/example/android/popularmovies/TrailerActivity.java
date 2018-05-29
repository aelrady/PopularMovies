package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.popularmovies.model.Trailer;

import java.util.ArrayList;

public class TrailerActivity extends AppCompatActivity {

    private ArrayList<Trailer> trailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        setTitle("Trailers");

        populateTrailerActivity();
    }

    private void populateTrailerActivity() {
        Intent intent = getIntent();
        trailers = intent.getParcelableArrayListExtra("trailers");
        Log.v("Show More Trailers: ", String.valueOf(trailers));
    }
}
