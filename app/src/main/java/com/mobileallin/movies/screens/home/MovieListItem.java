/*
package com.mobileallin.movies.screens.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileallin.movies.R;
import com.mobileallin.movies.models.Movie;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ViewConstructor")
public class MovieListItem extends FrameLayout {

    private final Picasso picasso;

    @BindView(R.id.movie_picture)
    ImageView avatarImage;

    @BindView(R.id.movie_title)
    TextView name;

   */
/* @BindView(R.id.repo_description)
    TextView description;

    @BindView(R.id.repo_updated_at)
    TextView updatedAt;
*//*

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.fullDate();

    public MovieListItem(Context context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
        inflate(getContext(), R.layout.movies_item, this);
        ButterKnife.bind(this);
    }

    public void setRepo(Movie movie) {
        Locale locale = getResources().getConfiguration().locale;

        name.setText(movie.getOriginalTitle());
//        description.setVisibility(TextUtils.isEmpty(githubRepo.getOriginalTitle()) ? GONE : VISIBLE);
//        description.setText(githubRepo.getOriginalTitle());


        picasso.load(movie.getFullPosterURL())
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(avatarImage);
    }
}
*/
