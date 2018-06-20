package com.example.android.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.database.MovieRoomDatabase;
import com.example.android.popularmovies.model.Movie;

public class MovieViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public MovieViewModel(MovieRoomDatabase database, int movieId) {
        movie = database.movieDao().getLiveDataMovie(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
