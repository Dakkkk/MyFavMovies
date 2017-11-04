package com.mobileallin.movies.screens.home.activity;

import android.content.Context;
import android.view.View;

import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.network.MovieDbController;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.screens.home.AdapterMovies;
import com.mobileallin.movies.utils.SortingCriteria;

import retrofit2.Call;

/**
 * Created by Dawid on 2017-11-04.
 */

public class HomeActivityPresenter {

    private HomeActivityView view;

    public HomeActivityPresenter(HomeActivityView view) {
        this.view = view;
    }

    public void loadMovies(MovieDbController movieDbController, Call<APIResults<Movie>> reposCall, MovieService movieService,
                           AdapterMovies adapterMovies, Context context, View view,
                           SortingCriteria sortingCriteria) {
        movieDbController.getSortedMovies(reposCall, movieService,
                adapterMovies, context, view, sortingCriteria);
    }
}
