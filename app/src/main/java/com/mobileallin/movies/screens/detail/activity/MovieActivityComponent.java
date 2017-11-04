package com.mobileallin.movies.screens.detail.activity;

import com.mobileallin.movies.dagger.MoviesApplicationComponent;

import dagger.Component;

/**
 * Created by Dawid on 2017-09-19.
 */
@MovieActivityScope
@Component(modules = MovieActivityModule.class, dependencies = MoviesApplicationComponent.class)
public interface MovieActivityComponent {
    void injectMovieActivity(MovieActivity movieActivity);
}
