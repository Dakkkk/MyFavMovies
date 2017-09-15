package com.mobileallin.movies.database;

import com.mobileallin.movies.MoviesApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 2017-09-14.
 */
@Module
public class MoviesDatabaseModule {
    private final MoviesDatabase moviesDatabase;

    public MoviesDatabaseModule(MoviesDatabase moviesDatabase) {
        this.moviesDatabase = moviesDatabase;
    }

    @Provides
    @MoviesApplicationScope
    public MoviesDatabase moviesDatabase() {
        return moviesDatabase;
    }
}

