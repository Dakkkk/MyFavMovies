package com.mobileallin.movies.screens.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.screens.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class AdapterMovies extends BaseAdapter {

    private final List<Movie> repoList = new ArrayList<>(0);
  private final Context context;
  private final Picasso picasso;

  @Inject
  public AdapterMovies(HomeActivity context, Picasso picasso) {
    this.context = context;
    this.picasso = picasso;
  }

  @Override
  public int getCount() {
      if (repoList == null) {
          return 0;
      }
    return repoList.size();
  }

  @Override
  public Movie getItem(int position) {
    return repoList.get(position);
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public long getItemId(int position) {
    return repoList.get(position).id;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    MovieListItem movieListItem;
    if(convertView == null) {
      movieListItem = new MovieListItem(context, picasso);
    } else {
      movieListItem = MovieListItem.class.cast(convertView);
    }

    movieListItem.setRepo(repoList.get(position));

    return movieListItem;
  }

  public void swapData(Collection<Movie> githubRepos) {
      if (githubRepos == null) return;
    repoList.clear();
    if(githubRepos != null) {
      repoList.addAll(githubRepos);
    }
    notifyDataSetChanged();
  }

}
