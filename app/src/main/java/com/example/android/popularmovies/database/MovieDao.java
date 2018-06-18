package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT id FROM movies")
    int[] getIds();

    @Query("SELECT id FROM movies")
    LiveData<int[]> getLiveDataIds();

    @Query("SELECT id, poster_path, title, release_date, vote_average, overview FROM movies WHERE id = :id LIMIT 1")
    LiveData<Movie> getLiveDataMovie(int id);

    @Query("SELECT id, poster_path, title, release_date, vote_average, overview FROM movies WHERE id = :id LIMIT 1")
    Movie getMovie(int id);

    @Query("SELECT poster_path FROM movies")
    List<String> getPosterPath();

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);
}