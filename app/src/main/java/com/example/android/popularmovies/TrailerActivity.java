package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.model.Trailer;

import java.util.ArrayList;

public class TrailerActivity extends AppCompatActivity {

    private ArrayList<Trailer> trailers;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private TrailerAdapter trailerAdapter;
    private ArrayList<String> trailerList;
    private TextView connectionTextView;

    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        setTitle("Trailers");

        connectionTextView = findViewById(R.id.no_connection);
        if (isConnected()) {
            populateTrailerActivity();
            connectionTextView.setVisibility(View.GONE);
        } else {
            connectionTextView.setVisibility(View.VISIBLE);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void populateTrailerActivity() {
        Intent intent = getIntent();
        trailers = intent.getParcelableArrayListExtra("trailers");

        trailerList = new ArrayList<>();
        for (int i = 0; i < trailers.size(); i++) {
            trailerList.add(YOUTUBE_BASE_URL + trailers.get(i).getKey());
        }

        recyclerView = findViewById(R.id.rv_trailers);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        trailerAdapter = new TrailerAdapter(TrailerActivity.this, trailerList);
        recyclerView.setAdapter(trailerAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(TrailerActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    };
}
