package com.mobileallin.movies;

import android.app.Activity;
import android.app.Application;

import com.mobileallin.movies.dagger.modules.ContextModule;
import com.mobileallin.movies.dagger.DaggerMoviesApplicationComponent;
import com.mobileallin.movies.dagger.MoviesApplicationComponent;
import com.mobileallin.movies.dagger.modules.MoviesDbControllerModule;
import com.mobileallin.movies.database.MoviesDatabase;
import com.mobileallin.movies.network.MovieDbController;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.utils.Initializer;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class MoviesApplication extends Application {

    private MoviesApplicationComponent component;

    public static MoviesApplication get(Activity activity) {
        return (MoviesApplication) activity.getApplication();
    }

    private MovieService movieService;

    private Picasso picasso;

    private MoviesDatabase database;

    private MovieDbController movieDbController;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerMoviesApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .moviesDbControllerModule(new MoviesDbControllerModule(new MovieDbController())) //ToDo check if this is fully correct
                .build();

        movieService = component.getMovieService();
        picasso = component.getPicasso();
        movieDbController = component.getMovieDbController();


        Initializer.init(this);
    }

    public MoviesApplicationComponent component() {
        return component;
    }
}