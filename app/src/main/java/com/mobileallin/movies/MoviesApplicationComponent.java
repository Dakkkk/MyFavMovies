package com.mobileallin.movies;

/*
import com.mobileallin.movies.database.MoviesDatabaseModule;
*/
import com.mobileallin.movies.network.MovieDbController;
import com.mobileallin.movies.network.MovieService;
import com.squareup.picasso.Picasso;

import dagger.Component;

@MoviesApplicationScope
@Component(modules = {MoviesServiceModule.class, PicassoModule.class, ActivityModule.class,
        MoviesDbControllerModule.class})
public interface MoviesApplicationComponent {

    Picasso getPicasso();

    MovieService getMovieService();

    MovieDbController getMovieDbController();

/*
    MoviesDatabase getMoviesDatabase();
*/
}
