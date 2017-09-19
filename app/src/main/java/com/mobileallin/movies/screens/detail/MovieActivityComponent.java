package com.mobileallin.movies.screens.detail;

import com.mobileallin.movies.MoviesApplicationComponent;

import dagger.Component;

/**
 * Created by Dawid on 2017-09-19.
 */
@MovieActivityScope
@Component(modules = MovieActivityModule.class, dependencies = MoviesApplicationComponent.class)
public interface MovieActivityComponent {
    void injectMovieActivity(MovieActivity movieActivity);
}
