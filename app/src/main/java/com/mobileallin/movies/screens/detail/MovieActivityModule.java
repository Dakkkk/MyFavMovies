package com.mobileallin.movies.screens.detail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 2017-09-19.
 */
@Module
public class MovieActivityModule {

    private final MovieActivity movieActivity;

    public MovieActivityModule(MovieActivity movieActivity) {
        this.movieActivity = movieActivity;
    }

    @Provides
    @MovieActivityScope
    public MovieActivity movieActivity() {
        return movieActivity;
    }
}