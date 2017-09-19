package com.mobileallin.movies.screens.detail;

import com.mobileallin.movies.MoviesApplicationComponent;
import com.mobileallin.movies.screens.HomeActivityScope;

import dagger.Component;

/**
 * Created by Dawid on 2017-09-19.
 */
@HomeActivityScope
@Component(modules = MovieActivityModule.class, dependencies = MoviesApplicationComponent.class)
public interface MovieActivityComponent {
    void injectMovieActivity(MovieActivity movieActivity);
}
