package com.example.android.popularmovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiClient {
    private static final String API_BASE_URL = "http://api.themoviedb.org/3/";

    public static Retrofit retrofit;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
