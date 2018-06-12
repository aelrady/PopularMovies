package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT id FROM movies")
    int[] getIds();

    @Query("SELECT poster_path FROM movies")
    List<String> getPosterPath();

    @Insert
    void insert(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Movie movie);

    @Delete
    void delete(Movie movie);
}
