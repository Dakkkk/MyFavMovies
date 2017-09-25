package com.mobileallin.movies.screens.home.activity;

import com.mobileallin.movies.screens.home.MovieClickModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = MovieClickModule.class)
public class HomeActivityModule {

    private final HomeActivity homeActivity;

    public HomeActivityModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @HomeActivityScope
    public HomeActivity homeActivity() {
        return homeActivity;
    }
}
