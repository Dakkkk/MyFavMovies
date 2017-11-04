package com.mobileallin.movies.dagger.modules;

import com.mobileallin.movies.dagger.MoviesApplicationScope;
import com.mobileallin.movies.network.MovieDbController;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 2017-09-25.
 */
@Module
public class MoviesDbControllerModule {

    private final MovieDbController movieDbController;

    public MoviesDbControllerModule(MovieDbController movieDbController) {
        this.movieDbController = movieDbController;
    }

    @Provides
    @MoviesApplicationScope
    public MovieDbController movieDbController() {
        return movieDbController;
    }
}
