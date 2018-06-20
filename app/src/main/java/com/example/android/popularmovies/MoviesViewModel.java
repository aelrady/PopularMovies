package com.example.android.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.database.MovieRoomDatabase;
import com.example.android.popularmovies.model.Movie;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        MovieRoomDatabase database = MovieRoomDatabase.getDatabase(this.getApplication());
        Log.d(TAG, "Actively retrieving movies from the database");
        movies = database.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
