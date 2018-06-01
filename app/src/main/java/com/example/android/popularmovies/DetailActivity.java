package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    @BindView(R.id.detail_network_exception) TextView detailNetworkExceptionTextView;
    @BindView(R.id.trailer_1) LinearLayout trailer;
    @BindView(R.id.trailer) TextView trailerTextView;
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

        MovieApiInterface movieApiInterface = MovieApiClient.getClient().create(MovieApiInterface.class);

        Call<TrailerResults> call = movieApiInterface.getTrailers(movie.getId(), BuildConfig.API_KEY);
        call.enqueue(new Callback<TrailerResults>() {
            @Override
            public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {
                Log.v("Response: ", String.valueOf(response.body()));
                if (response.body() != null) {
                    trailers = (ArrayList<Trailer>) response.body().getResults();
                    Log.v("Trailers List: ", String.valueOf(trailers));
                } else {
                    trailer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TrailerResults> call, Throwable t) {
                detailNetworkExceptionTextView.setVisibility(View.VISIBLE);
            }
        });

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(YOUTUBE_BASE_URL + trailers.get(0).getKey()));
                startActivity(intent);
            }
        });

        showMoreTrailersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, TrailerActivity.class);
                intent.putParcelableArrayListExtra("trailers", trailers);
                startActivity(intent);
            }
        });
    }


    public void callReviews() {

        MovieApiInterface movieApiInterface = MovieApiClient.getClient().create(MovieApiInterface.class);

        Call<ReviewResults> call = movieApiInterface.getReviews(movie.getId(), BuildConfig.API_KEY);
        call.enqueue(new Callback<ReviewResults>() {

            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                reviews = (ArrayList<Review>) response.body().getResults();
                Log.v("Reviews: ", String.valueOf(reviews));
                if (!reviews.isEmpty()) {
                    authorTextView.setText(reviews.get(0).getAuthor());
                    reviewTextView.setText(reviews.get(0).getContent());
                } else {
                    review.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {
                detailNetworkExceptionTextView.setVisibility(View.VISIBLE);
            }
        });

        showMoreReviewsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putParcelableArrayListExtra("reviews", reviews);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("trailers", trailers);
        outState.putParcelableArrayList("reviews", reviews);
        super.onSaveInstanceState(outState);
    }
}
