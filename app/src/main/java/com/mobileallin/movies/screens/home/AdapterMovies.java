package com.mobileallin.movies.screens.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileallin.movies.R;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.screens.home.activity.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MoviesViewHolder> {

    private Context mContext;
    private Picasso picasso;
    private List<Movie> moviesList = new ArrayList<>(0);


    public MovieOnItemClick mOnMovieClick;

    @Inject
    public AdapterMovies(HomeActivity context, Picasso picasso, MovieOnItemClick onClick) {
        this.mContext = context;
        this.picasso = picasso;
        this.mOnMovieClick = onClick;
    }

    /**
     * Handles click on movie in grid view
     */
    public interface MovieOnItemClick {
        void onClick(Movie movie);
    }

    public void setMovies(List<Movie> movies) {
        this.moviesList = movies;
        notifyDataSetChanged();
    }


    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movies_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.setMovie(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    public void swapData(Collection<Movie> githubRepos) {
        if (githubRepos == null) return;
        moviesList.clear();
        if (githubRepos != null) {
            moviesList.addAll(githubRepos);
        }
        notifyDataSetChanged();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMovieTitleTextView;
        private ImageView mMoviePosterImageView;

        MoviesViewHolder(View itemView) {
            super(itemView);
            mMovieTitleTextView = itemView.findViewById(R.id.movie_title);
            mMoviePosterImageView = itemView.findViewById(R.id.movie_picture);

            itemView.setOnClickListener(this);
        }

        void setMovie(Movie movie) {
            mMovieTitleTextView.setText(movie.getOriginalTitle());
            Picasso.with(mContext).load(movie.getFullPosterURL()).into(mMoviePosterImageView);
        }

        @Override
        public void onClick(View v) {
            if (mOnMovieClick != null) {
                mOnMovieClick.onClick(moviesList.get(getAdapterPosition()));
            }
        }
    }
}
