package com.mobileallin.movies.screens.detail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mobileallin.movies.models.Video;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.screens.detail.adapter.AdapterDetailReviews;
import com.mobileallin.movies.screens.detail.adapter.AdapterDetailVideos;
import com.mobileallin.movies.screens.detail.adapter.VideoClickModule;
import com.mobileallin.movies.utils.DetailCallCriteria;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dawid on 2017-08-31.
 */


public class MovieActivity extends AppCompatActivity implements AdapterDetailVideos.VideoOnClickHandler {

    @BindView(R.id.detail_reviews_grid)
    RecyclerView reviewsListView;

    @BindView(R.id.detail_videos_grid)
    RecyclerView videosListView;

    @Inject
    MovieService movieService;

    @Inject
    AdapterDetailReviews adapterReviews;

    @Inject
    AdapterDetailVideos adapterVideos;

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        MovieActivityComponent component = DaggerMovieActivityComponent.builder()
                .movieActivityModule(new MovieActivityModule(this))
                .moviesApplicationComponent(MoviesApplication.get(this).component())
                .videoClickModule(new VideoClickModule(this))
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("MoviePict", String.valueOf(movie.getPosterURL()));

        ImageView moviePicture = findViewById(R.id.movie_poster_detail);
        Picasso.with(getApplicationContext()).load(movie.getFullPosterURL()).into(moviePicture);

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

        RecyclerView.LayoutManager managerRev = new LinearLayoutManager(this);
        reviewsListView.setLayoutManager(managerRev);
        reviewsListView.setAdapter(adapterReviews);

        RecyclerView.LayoutManager managerVid = new LinearLayoutManager(this);
        videosListView.setLayoutManager(managerVid);
        videosListView.setAdapter(adapterVideos);

        // load reviews, videos from the database or make an API call

        if (movie.getReviews() == null || movie.getReviews().size() == 0) {
            makeApiCall(DetailCallCriteria.REVIEWS);
        }

        if (movie.getVideos() == null || movie.getVideos().size() == 0) {
            makeApiCall(DetailCallCriteria.VIDEOS);
        }


/*        makeApiCall(DetailCallCriteria.REVIEWS);

        makeApiCall(DetailCallCriteria.VIDEOS);*/


   /*     reviewsCall.enqueue(new Callback<APIResults<Review>>() {
            @Override
            public void onResponse(Call<APIResults<Review>> call, Response<APIResults<Review>> response) {
                Log.i("REVIEWS response", String.valueOf(response.body().results.get(0).getContent()));
                adapterReviews.swapData(response.body().results);
            }

            @Override
            public void onFailure(Call<APIResults<Review>> call, Throwable t) {
                Log.e("API failure", t.getMessage());
                Toast.makeText(MovieActivity.this, "Error getting reviews " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

/*
        TextView reviewsText = findViewById(R.id.movie_reviews);
        reviewsText.setText(String.valueOf(movie.getReviews()));*/

        MaterialFavoriteButton favoriteButton = findViewById(R.id.favourite_button);
        favoriteButton.setFavorite(movie.isFavourite());

        Log.i("isFavourite", String.valueOf(movie.isFavourite()));

        Log.d("Exists", String.valueOf(movie.exists()));

        favoriteButton.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (movie == null) return;
                    movie.setFavourite(favorite);
                    try {
                        if (movie.exists()) {
                            Log.d("fav", "update");
                            movie.update();
                        } else {
                            Log.d("fav", "save");
                            movie.save();
                        }
                    } catch (Exception ignore) {
                    }
                    Log.d("favChange", String.valueOf(movie.isFavourite()));
                });

    }

    public void makeApiCall(DetailCallCriteria criteria) {
        if (criteria == DetailCallCriteria.REVIEWS) {
            Call<APIResults<Review>> call = movieService.getReviews(movie.getId());
            call.enqueue(new Callback<APIResults<Review>>() {
                @Override
                public void onResponse(Call<APIResults<Review>> call, Response<APIResults<Review>> response) {
/*
                    if (response.body() == null) return;
*/
                    adapterReviews.swapData(response.body().results);
                }

                @Override
                public void onFailure(Call<APIResults<Review>> call, Throwable t) {
                    Log.e("REVIEWS failure", t.getMessage());
                    Toast.makeText(MovieActivity.this, "Error getting reviews " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MovieActivity.this, "Error getting reviews " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void setReviews(List<Review> reviews) {
        if (reviews == null || reviews.size() == 0) {
            Toast.makeText(this, "No favourite movies!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("setMovies, fav", reviews.get(0).toString());
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).exists()) reviews.get(i).load();
        }
        //movies.stream().filter(Movie::exists).forEach(Movie::load);
        adapterReviews.setMovies(reviews);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movie != null) {
            outState.putParcelable(MOVIE_KEY, Parcels.wrap(movie));
        }
    }

    @Override
    public void onClick(Video video, boolean share) {
        String videoID = video.getKey();
        if (share) {
            startActivity(createShareVidIntent(videoID));
        } else {
            if (video.isYouTubeVideo()) {
                watchYoutubeVideo(videoID);
            } else {
                Toast.makeText(this, "Don't know how to open video on site " + video.getSite(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private Intent createShareVidIntent(String videoID) {
        String content = "Watch " + movie.getOriginalTitle() + " " +
                youtubeUrl(videoID) + " " + getString(R.string.app_hash_tag);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        return shareIntent;
    }

    private void watchYoutubeVideo(String videoID) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoID)));
        } catch (ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl(videoID))));
        }
    }

    private static String youtubeUrl(String videoID) {
        return "https://youtu.be/" + videoID;
    }
}
