package com.mobileallin.movies.screens.home.activity.presenter;

import android.content.Context;
import android.view.View;

import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.models.Movie_Table;
import com.mobileallin.movies.network.MovieDbController;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.screens.home.AdapterMovies;
import com.mobileallin.movies.screens.home.activity.view.HomeActivityView;
import com.mobileallin.movies.utils.SortingCriteria;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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

    public SortingCriteria selectSortedMovies(SortingCriteria currentCriteria, SortingCriteria criteria) {
        if (currentCriteria == criteria) {
            return currentCriteria;
        }

        if (criteria == SortingCriteria.FAVORITE) {
            currentCriteria = criteria;
            SQLite.select()
                    .from(Movie.class)
                    .where(Movie_Table.favourite.is(true))
                    .async()
                    .queryListResultCallback((__, movies) -> view.displayMovies(movies))
                    .execute();
        }

        return currentCriteria;

    }
}
