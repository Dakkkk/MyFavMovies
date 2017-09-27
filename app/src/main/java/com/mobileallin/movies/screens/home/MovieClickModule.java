package com.mobileallin.movies.screens.home;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 2017-09-14.
 */
@Module
public class MovieClickModule {

    private final AdapterMovies.MovieOnItemClick movieClick;

    public MovieClickModule(AdapterMovies.MovieOnItemClick click) {
        this.movieClick = click;
    }

    @Provides
    public AdapterMovies.MovieOnItemClick movieClick() {
        return movieClick;
    }
}

