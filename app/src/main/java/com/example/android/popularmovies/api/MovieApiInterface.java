package com.example.android.popularmovies.api;

import com.example.android.popularmovies.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("movie/{sort_param}")
    Call<MovieResults> getMovies(
            @Path("sort_param") String sortParam,
            @Query("api_key") String apiKey);
}
