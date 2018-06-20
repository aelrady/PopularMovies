package com.example.android.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.database.MovieRoomDatabase;

public class IdsViewModel extends AndroidViewModel {

    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private LiveData<int[]> ids;

    public IdsViewModel(@NonNull Application application) {
        super(application);
        MovieRoomDatabase database = MovieRoomDatabase.getDatabase(this.getApplication());
        Log.d(TAG, "Actively retrieving ids from the database");
        ids = database.movieDao().getLiveDataIds();
    }

    public LiveData<int[]> getIds() {
        return ids;
    }
}
