package com.mobileallin.movies.screens.detail.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.mobileallin.movies.MoviesApplication;
import com.mobileallin.movies.R;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.models.Video;
import com.mobileallin.movies.network.MovieDbController;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.screens.detail.adapter.AdapterDetailReviews;
import com.mobileallin.movies.screens.detail.adapter.AdapterDetailVideos;
import com.mobileallin.movies.screens.detail.adapter.VideoClickModule;
import com.mobileallin.movies.utils.DetailCallCriteria;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mobileallin.movies.R.id.movie_poster_detail;
import static com.mobileallin.movies.R.id.movie_rating;
import static com.mobileallin.movies.R.id.movie_synopsis;
import static com.mobileallin.movies.R.id.movie_title_detail;
import static com.mobileallin.movies.R.id.release_data;
import static com.mobileallin.movies.R.id.show_reviews_btn;
import static com.mobileallin.movies.R.id.show_videos_btn;

/**
 * Created by Dawid on 2017-08-31.
 */


public class MovieActivity extends AppCompatActivity implements AdapterDetailVideos.VideoOnClickHandler {

    @BindView(R.id.detail_reviews_grid)
    RecyclerView reviewsListView;

    @BindView(R.id.detail_videos_grid)
    RecyclerView videosListView;

    @BindView(R.id.detail_view)
    ScrollView detailView;

    @Inject
    MovieService movieService;

    @Inject
    AdapterDetailReviews adapterReviews;

    @Inject
    AdapterDetailVideos adapterVideos;

    @Inject
    MovieDbController movieDbController;

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
            }
        } catch (Exception ignore) {
        }
        if (movie == null) {
            Toast.makeText(this, getResources().getString(R.string.no_movie_error), Toast.LENGTH_LONG).show();
            return;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView moviePicture = detailView.findViewById(movie_poster_detail);
        Picasso.with(getApplicationContext()).load(movie.getFullPosterURL()).into(moviePicture);

        TextView titleText = detailView.findViewById(movie_title_detail);
        titleText.setText(movie.getOriginalTitle());

        TextView movieSynopsis = detailView.findViewById(movie_synopsis);
        movieSynopsis.setText(movie.getPlotSynopsis());

        TextView releaseData = detailView.findViewById(release_data);
        releaseData.setText(movie.getReleaseDate());

        TextView movieRating = detailView.findViewById(movie_rating);
        movieRating.setText(String.valueOf(movie.getUserRating()));

        Button showReviewsBtn = detailView.findViewById(show_reviews_btn);
        Button showVideosBtn = detailView.findViewById(show_videos_btn);

        reviewsListView.setVisibility(View.GONE);
        videosListView.setVisibility(View.GONE);

        showReviewsBtn.setOnClickListener(view -> {
            if (reviewsListView.getVisibility() == View.GONE) {
                reviewsListView.setVisibility(View.VISIBLE);
            } else reviewsListView.setVisibility(View.GONE);
        });

        showVideosBtn.setOnClickListener(view -> {
            if (videosListView.getVisibility() == View.GONE) {
                videosListView.setVisibility(View.VISIBLE);
                videosListView.smoothScrollToPosition(videosListView.getAdapter().getItemCount() - 1);
            } else videosListView.setVisibility(View.GONE);
        });

        RecyclerView.LayoutManager managerRev = new LinearLayoutManager(this);
        reviewsListView.setLayoutManager(managerRev);
        reviewsListView.setAdapter(adapterReviews);

        RecyclerView.LayoutManager managerVid = new LinearLayoutManager(this);
        videosListView.setLayoutManager(managerVid);
        videosListView.setAdapter(adapterVideos);

        // load reviews, videos from the database or make an API call
        if (movie.getReviews() == null || movie.getReviews().size() == 0) {
            movieDbController.makeApiCall(movieService, adapterReviews, adapterVideos,
                    this, movie, DetailCallCriteria.REVIEWS);
        }
        if (movie.getVideos() == null || movie.getVideos().size() == 0) {
            movieDbController.makeApiCall(movieService, adapterReviews, adapterVideos,
                    this, movie, DetailCallCriteria.VIDEOS);

        }


        MaterialFavoriteButton favoriteButton = findViewById(R.id.favourite_button);
        favoriteButton.setColor(R.color.white);
        favoriteButton.setFavorite(movie.isFavourite());

        favoriteButton.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (movie == null) return;
                    movie.setFavourite(favorite);
                    try {
                        if (movie.exists()) {
                            movie.update();
                        } else {
                            movie.save();
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
