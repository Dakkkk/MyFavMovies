package com.mobileallin.movies.screens.home.activity;

import com.mobileallin.movies.dagger.MoviesApplicationComponent;

import dagger.Component;

@HomeActivityScope
@Component(modules = HomeActivityModule.class, dependencies = MoviesApplicationComponent.class)
public interface HomeActivityComponent {

    void injectHomeActivity(HomeActivity homeActivity);
}
