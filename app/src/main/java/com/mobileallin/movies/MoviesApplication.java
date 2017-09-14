package com.mobileallin.movies;

import android.app.Activity;
import android.app.Application;

import com.mobileallin.movies.network.MovieService;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class MoviesApplication extends Application {

    private MoviesApplicationComponent component;

    public static MoviesApplication get(Activity activity) {
        return (MoviesApplication) activity.getApplication();
    }

    private MovieService movieService;

    private Picasso picasso;

    //   Activity

    //GithubService   picasso

    //retrofit    OkHttp3Downloader

    //gson      okhttp

    //      logger    cache

    //      timber           file

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerMoviesApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        movieService = component.getMovieService();
        picasso = component.getPicasso();
    }

    public MoviesApplicationComponent component() {
        return component;
    }
}