package com.mobileallin.movies.network;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.mobileallin.movies.R;
import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.models.Review;
import com.mobileallin.movies.models.Video;
import com.mobileallin.movies.screens.detail.adapter.AdapterDetailReviews;
import com.mobileallin.movies.screens.detail.adapter.AdapterDetailVideos;
import com.mobileallin.movies.screens.home.AdapterMovies;
import com.mobileallin.movies.utils.DetailCallCriteria;
import com.mobileallin.movies.utils.SortingCriteria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller for making endpoint API calls
 */

//ToDo rewrite the functions in order to get more DRY code
public class MovieDbController {

    /*    Detail class*/
    public void makeApiCall(MovieService movieService, AdapterDetailReviews adapterReviews,
                            AdapterDetailVideos adapterVideos, Context context, Movie movie,
                            DetailCallCriteria criteria) {
        if (criteria == DetailCallCriteria.REVIEWS) {
            Call<APIResults<Review>> call = movieService.getReviews(movie.getId());
            call.enqueue(new Callback<APIResults<Review>>() {
                @Override
                public void onResponse(Call<APIResults<Review>> call, Response<APIResults<Review>> response) {
                    if (response.body() == null) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapterReviews.swapData(response.body().results);
                }

                @Override
                public void onFailure(Call<APIResults<Review>> call, Throwable t) {
                    Log.e("REVIEWS failure", t.getMessage());
                    Toast.makeText(context, "Error getting reviews " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (criteria == DetailCallCriteria.VIDEOS) {
            Call<APIResults<Video>> call = movieService.getVideos(movie.getId());
            call.enqueue(new Callback<APIResults<Video>>() {
                @Override
                public void onResponse(Call<APIResults<Video>> call, Response<APIResults<Video>> response) {
/*
                    if (response.body() == null) return;
*/
                    adapterVideos.swapData(response.body().results);
                }

                @Override
                public void onFailure(Call<APIResults<Video>> call, Throwable t) {
                    Log.e("Videos failure", t.getMessage());
                    Toast.makeText(context, "Error getting reviews " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*  HomeActivity class*/
    public void getSortedMovies(Call<APIResults<Movie>> reposCall, MovieService movieService,
                                AdapterMovies adapterMovies, Context context, ScrollView homeView,
                                SortingCriteria sortingCriteria) {
        /*Show progress bar*/
        showLoadingIndicator(homeView);

        reposCall = movieService.getMostPopular();
        if (sortingCriteria == SortingCriteria.MOST_POPULAR) {
            reposCall = movieService.getMostPopular();
        } else if (sortingCriteria == SortingCriteria.TOP_RATED) {
            reposCall = movieService.getTopRated();
        }
        reposCall.enqueue(new Callback<APIResults<Movie>>() {
            @Override
            public void onResponse(Call<APIResults<Movie>> call, Response<APIResults<Movie>> response) {
                if (response.body().results != null && response.body().results.size() > 0) {
                    hideLoadingIndicator(homeView);
                }
                adapterMovies.swapData(response.body().results);
            }

            @Override
            public void onFailure(Call<APIResults<Movie>> call, Throwable t) {
                Log.e("API failure", t.getMessage());
                Toast.makeText(context, "Error getting movies " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoadingIndicator(ScrollView homeView) {
        homeView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator(ScrollView homeView) {
        homeView.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
    }
}
