package com.mobileallin.movies.screens.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.mobileallin.movies.R;
import com.mobileallin.movies.models.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * Created by Dawid on 2017-08-31.
 */

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";
    Movie movie;

    //testing
    Movie movie1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        try {
            if (intent != null) {
                movie = Parcels.unwrap(intent.getParcelableExtra(MOVIE_KEY));
                Log.d("Fmovie", String.valueOf(movie.isFavourite()));
                movie1 = Parcels.unwrap(savedInstanceState.getParcelable(MOVIE_KEY));
                Log.d("Fmovie1", String.valueOf(movie1.isFavourite()));

            }
        } catch (Exception ignore) {
        }
        if (movie == null) {
            Toast.makeText(this, getResources().getString(R.string.no_movie_error), Toast.LENGTH_LONG).show();
            return;
        }
        TextView titleText = findViewById(R.id.movie_title_detail);
        Log.d("DetailMovie", String.valueOf(movie.getOriginalTitle()));
        titleText.setText(movie.getOriginalTitle());

        ImageView moviePicture = findViewById(R.id.movie_poster_detail);
        Picasso.with(this).load(movie.getFullPosterURL()).into(moviePicture);

        MaterialFavoriteButton favoriteButton = findViewById(R.id.favourite_button);

        if(movie.isFavourite()) favoriteButton.setFavorite(true);

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
                    } catch (Exception ignore){
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
