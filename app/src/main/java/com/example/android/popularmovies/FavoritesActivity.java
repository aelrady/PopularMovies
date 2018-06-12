package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.database.MovieRoomDatabase;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private Movie movie;
    private MovieRoomDatabase mMovieRoomDatabase;

    int id;
    int[] ids;

    @BindView(R.id.star) ImageView star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mMovieRoomDatabase = MovieRoomDatabase.getDatabase(getApplicationContext());

        populateDetailActivity();

        id = movie.getId();
        ids = mMovieRoomDatabase.movieDao().getIds();
        if (isInDatabase(ids, id)) {
            star.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        updateFavorites();
    }

    private void populateDetailActivity() {
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        Log.v("Movies: ", String.valueOf(movie));

        String moviePosterUrl = movie.getPosterPath();
        String fullMoviePosterUrl = IMAGE_BASE_URL + moviePosterUrl;
        ImageView moviePosterImageView = findViewById(R.id.movie_poster);
        Picasso.with(this).load(fullMoviePosterUrl).resize(385, 579).into(moviePosterImageView);

        String title = movie.getTitle();
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(title);

        String date = movie.getReleaseDate();
        TextView dateTextView = findViewById(R.id.release_date);
        dateTextView.setText(formatDate(date));

        Float rating = movie.getVoteAverage();
        String formattedRating = formatRating(rating);
        TextView voteTextView = findViewById(R.id.rating);
        voteTextView.setText(formattedRating);

        String overview = movie.getOverview();
        TextView overviewTextView = findViewById(R.id.overview);
        overviewTextView.setText(overview);
    }

    public String formatDate(String date) {
        String[] dateComponents = date.split("-");

        String year = dateComponents[0];

        String month = dateComponents[1];
        String formattedMonth = null;
        switch (month) {
            case "01":
                formattedMonth = "January";
                break;
            case "02":
                formattedMonth = "February";
                break;
            case "03":
                formattedMonth = "March";
                break;
            case "04":
                formattedMonth = "April";
                break;
            case "05":
                formattedMonth = "May";
                break;
            case "06":
                formattedMonth = "June";
                break;
            case "07":
                formattedMonth = "July";
                break;
            case "08":
                formattedMonth = "August";
                break;
            case "09":
                formattedMonth = "September";
                break;
            case "10":
                formattedMonth = "October";
                break;
            case "11":
                formattedMonth = "November";
                break;
            case "12":
                formattedMonth = "December";
                break;
        }

        String day = dateComponents[2];
        String formattedDay;
        if (day.charAt(0) == 0) {
            formattedDay = (day).substring(1);
        } else {
            formattedDay = day;
        }

        return formattedMonth + " " + formattedDay + ", " + year;
    }

    public String formatRating(Float rating) {
        return rating + "/10";
    }

    public void updateFavorites() {
        star.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                id = movie.getId();
                ids = mMovieRoomDatabase.movieDao().getIds();
                Log.v("Favorite: ", String.valueOf(isInDatabase(ids, id)));
                String moviePosterUrl = movie.getPosterPath();
                String title = movie.getTitle();
                String date = movie.getReleaseDate();
                Float rating = movie.getVoteAverage();
                String overview = movie.getOverview();
                Movie movie = new Movie(id, moviePosterUrl, title, date, rating, overview);
                if (!isInDatabase(ids, id)) {
                    star.setImageResource(R.drawable.ic_star_yellow_24dp);
                    mMovieRoomDatabase.movieDao().insert(movie);
                } else {
                    star.setImageResource(R.drawable.ic_star_border_black_24dp);
                    mMovieRoomDatabase.movieDao().delete(movie);
                }
            }
        });
    }

    public static boolean isInDatabase(final int[] ids, final int id) {
        boolean favorite = false;
        for(int i : ids) {
            if(i == id) {
                favorite = true;
                break;
            }
        }
        return favorite;
    }
}