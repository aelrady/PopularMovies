package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.api.MovieApiClient;
import com.example.android.popularmovies.api.MovieApiInterface;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.MovieResults;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private String sort_param = "popular";
    private GridLayoutManager gridLayoutManager;
    private ArrayList<String> picturePathList;
    private ArrayList<Movie> movies;
    private MovieAdapter movieAdapter;

    private RecyclerView recyclerView;

    @BindView(R.id.no_connection) TextView connectionTextView;
    @BindView(R.id.network_exception) TextView networkExceptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        networkExceptionTextView.setVisibility(View.GONE);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            if (isConnected()) {
                populateGrid();
                connectionTextView.setVisibility(View.GONE);
            } else {
                connectionTextView.setVisibility(View.VISIBLE);
            }
        } else {
            movies = savedInstanceState.getParcelableArrayList("movies");
            picturePathList = savedInstanceState.getStringArrayList("pictures");
            recyclerView = findViewById(R.id.rv_movies);
            gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            movieAdapter = new MovieAdapter(MainActivity.this, picturePathList, movies);
            recyclerView.setAdapter(movieAdapter);
            connectionTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movies);
        outState.putStringArrayList("pictures", picturePathList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isConnected()) {
            connectionTextView.setVisibility(View.GONE);
            if (item.getItemId() == R.id.most_popular) {
                sort_param = "popular";
                populateGrid();
            } else if (item.getItemId() == R.id.highest_rated) {
                sort_param = "top_rated";
                populateGrid();
            } else {
                return super.onOptionsItemSelected(item);
            }
        } else {
            connectionTextView.setVisibility(View.VISIBLE);
        }
        return true;
    }

    public void populateGrid() {

        recyclerView = findViewById(R.id.rv_movies);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        MovieApiInterface movieApiInterface = MovieApiClient.getClient().create(MovieApiInterface.class);

        Call<MovieResults> call = movieApiInterface.getMovies(sort_param, BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                movies = (ArrayList<Movie>) response.body().getResults();
                picturePathList = new ArrayList<>();
                for (int i = 0; i < movies.size(); i++) {
                    picturePathList.add(IMAGE_BASE_URL + movies.get(i).getPosterPath());
                }
                movieAdapter = new MovieAdapter(MainActivity.this, picturePathList, movies);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                networkExceptionTextView.setVisibility(View.VISIBLE);
            }
        });
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
