package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.api.MovieApiClient;
import com.example.android.popularmovies.api.MovieApiInterface;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.ReviewResults;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.model.TrailerResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;
    private Movie movie;

    private static final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";
    private ArrayList<String> trailerPathList;
    private ArrayList<String> reviewList;
    private ArrayList<String> reviewAuthorList;

    @BindView(R.id.detail_network_exception) TextView detailNetworkExceptionTextView;
    @BindView(R.id.trailer_1) LinearLayout trailer;
    @BindView(R.id.show_more_trailers) TextView showMoreTrailersTextView;
    @BindView(R.id.review_1) LinearLayout review;
    @BindView(R.id.show_more_reviews) TextView showMoreReviewsTextView;
    @BindView(R.id.review_text) TextView reviewTextView;
    @BindView(R.id.author) TextView authorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        detailNetworkExceptionTextView.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        populateDetailActivity();

        callTrailers();

        callReviews();
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


    public void callTrailers() {

        int movieId = movie.getId();

        MovieApiInterface movieApiInterface = MovieApiClient.getClient().create(MovieApiInterface.class);

        Call<TrailerResults> call = movieApiInterface.getTrailers(movieId, BuildConfig.API_KEY);
        call.enqueue(new Callback<TrailerResults>() {
            @Override
            public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {
                trailers = (ArrayList<Trailer>) response.body().getResults();
                trailerPathList = new ArrayList<>();
                for (int i = 0; i < trailers.size(); i++) {
                    trailerPathList.add(TRAILER_BASE_URL + trailers.get(i).getKey());
                }
                Log.v("Trailers: ", String.valueOf(trailerPathList));


            }

            @Override
            public void onFailure(Call<TrailerResults> call, Throwable t) {
                detailNetworkExceptionTextView.setVisibility(View.VISIBLE);
            }
        });

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Trailer 1", Toast.LENGTH_SHORT).show();
            }
        });

        showMoreTrailersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "More trailers", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void callReviews() {

        int movieId = movie.getId();

        MovieApiInterface movieApiInterface = MovieApiClient.getClient().create(MovieApiInterface.class);

        Call<ReviewResults> call = movieApiInterface.getReviews(movieId, BuildConfig.API_KEY);
        call.enqueue(new Callback<ReviewResults>() {
            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                reviews = (ArrayList<Review>) response.body().getResults();
                reviewList = new ArrayList<>();
                reviewAuthorList = new ArrayList<>();
                for (int i = 0; i < reviews.size(); i++) {
                    reviewList.add(reviews.get(i).getContent());
                    reviewAuthorList.add(reviews.get(i).getAuthor());
                }
                Log.v("Authors: ", String.valueOf(reviewAuthorList));
                Log.v("Reviews: ", String.valueOf(reviewList));


            }

            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {
                detailNetworkExceptionTextView.setVisibility(View.VISIBLE);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Review 1", Toast.LENGTH_SHORT).show();
            }
        });

        showMoreReviewsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "More reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
