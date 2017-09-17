package com.mobileallin.movies.database;

import com.mobileallin.movies.MoviesApplicationScope;
import com.mobileallin.movies.models.Movie;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 2017-09-14.
 */
@Module
public class MoviesDatabaseModule {
    private final MoviesDatabase moviesDatabase;
    private final Movie movie;

    public MoviesDatabaseModule(MoviesDatabase moviesDatabase, Movie movie) {
        this.moviesDatabase = moviesDatabase;
        this.movie = movie;
    }

    @Provides
    @MoviesApplicationScope
    public MoviesDatabase moviesDatabase() {
        return moviesDatabase;
    }

    @Provides
    public Movie movie () {
        return movie;
    }
}

