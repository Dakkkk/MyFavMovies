package com.mobileallin.movies.dagger;

import com.mobileallin.movies.dagger.modules.ActivityModule;
import com.mobileallin.movies.dagger.modules.MoviesDbControllerModule;
import com.mobileallin.movies.dagger.modules.MoviesServiceModule;
import com.mobileallin.movies.dagger.modules.PicassoModule;
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
}
