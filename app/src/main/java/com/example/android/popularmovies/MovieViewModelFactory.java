package com.example.android.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.popularmovies.database.MovieRoomDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRoomDatabase mDb;
    private final int mMovieId;

    public MovieViewModelFactory(MovieRoomDatabase database, int movieId) {
        mDb = database;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieViewModel(mDb, mMovieId);
    }
}
