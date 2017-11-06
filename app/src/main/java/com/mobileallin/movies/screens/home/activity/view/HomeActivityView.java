package com.mobileallin.movies.screens.home.activity.view;

import com.mobileallin.movies.models.Movie;

import java.util.List;

/**
 * Created by Dawid on 2017-11-04.
 */

public interface HomeActivityView {

    void displayMovies(List<Movie> movies);

    void displayNoMovies();

    void displayErrorMessage(String message);

    void handleNoInternetConnection();
}
