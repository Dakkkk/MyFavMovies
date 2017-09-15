package com.mobileallin.movies;

import com.mobileallin.movies.database.MoviesDatabaseModule;
import com.mobileallin.movies.database.MoviesDatabase;
import com.mobileallin.movies.network.MovieService;
import com.squareup.picasso.Picasso;

import dagger.Component;

@MoviesApplicationScope
@Component(modules = {MoviesServiceModule.class, PicassoModule.class, ActivityModule.class, MoviesDatabaseModule.class})
public interface MoviesApplicationComponent {

    Picasso getPicasso();

    MovieService getMovieService();

    MoviesDatabase getMoviesDatabase();
}
