package com.mobileallin.movies.screens.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.mobileallin.movies.MoviesApplication;
import com.mobileallin.movies.R;
import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.models.Review;
import com.mobileallin.movies.network.MovieService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dawid on 2017-08-31.
 */



public class MovieActivity extends AppCompatActivity {

    @Inject
    MovieService movieService;

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        MovieActivityComponent component = DaggerMovieActivityComponent.builder()
                .movieActivityModule(new MovieActivityModule(this))
                .moviesApplicationComponent(MoviesApplication.get(this).component())
                .build();

        component.injectMovieActivity(this);

        Intent intent = getIntent();
        try {
            if (intent != null) {
                movie = Parcels.unwrap(intent.getParcelableExtra(MOVIE_KEY));
                Log.d("Fmovie", String.valueOf(movie.isFavourite()));

            }
        } catch (Exception ignore) {
        }
        if (movie == null) {
            Toast.makeText(this, getResources().getString(R.string.no_movie_error), Toast.LENGTH_LONG).show();
            return;
        }
        ImageView moviePicture = findViewById(R.id.movie_poster_detail);
        Picasso.with(getApplicationContext()).load(movie.getPosterURL()).into(moviePicture);

        TextView titleText = findViewById(R.id.movie_title_detail);
        titleText.setText(movie.getOriginalTitle());

        TextView synopsisText = findViewById(R.id.movie_synopsis);
        Log.d("Synopsis", String.valueOf(movie.getPlotSynopsis()));
        synopsisText.setText(movie.getPlotSynopsis());

        TextView releaseText = findViewById(R.id.release_data);
        Log.d("Rdate", String.valueOf(movie.getReleaseDate()));
        releaseText.setText(movie.getReleaseDate());

 /*       VideoView videoView = findViewById(R.id.detail_video);
        if (movie.getVideos() != null) {
            Log.i("videos", movie.getVideos().toString());
            videoView.setVideoPath(movie.getVideos().get(1).getSite());
        }
*/
        TextView ratingText = findViewById(R.id.movie_rating);
        ratingText.setText(String.valueOf(movie.getUserRating()));

        Call<APIResults<Review>> reviewsCall;

        reviewsCall = movieService.getReviews(movie.getId());
        Log.i("Detail-reviews", String.valueOf(reviewsCall));

        reviewsCall.enqueue(new Callback<APIResults<Review>>() {
            @Override
            public void onResponse(Call<APIResults<Review>> call, Response<APIResults<Review>> response) {
                Log.i("REVIEWS response", String.valueOf(response.body().results.get(0).getContent()));
            }

            @Override
            public void onFailure(Call<APIResults<Review>> call, Throwable t) {
                Log.e("API failure", t.getMessage());
                Toast.makeText(MovieActivity.this, "Error getting reviews " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

/*
        TextView reviewsText = findViewById(R.id.movie_reviews);
        reviewsText.setText(String.valueOf(movie.getReviews()));*/

        MaterialFavoriteButton favoriteButton = findViewById(R.id.favourite_button);

        Log.i("isFavourite", String.valueOf(movie.isFavourite()));

        if (movie.isFavourite()) {
            favoriteButton.setFavorite(true);
        }

        favoriteButton.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (movie == null) return;
                    // save movie
                    try {
                        this.movie.setFavourite(!movie.isFavourite());
                        if (this.movie.exists()) {
                            Log.d("fav", "update");
                            this.movie.update();
                        } else {
                            Log.d("fav", "save");
                            this.movie.save();
                        }
                    } catch (Exception ignore) {
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movie != null) {
            outState.putParcelable(MOVIE_KEY, Parcels.wrap(movie));
        }
    }
}
