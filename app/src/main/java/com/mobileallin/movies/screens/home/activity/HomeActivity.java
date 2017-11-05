package com.mobileallin.movies.screens.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.mobileallin.movies.MoviesApplication;
import com.mobileallin.movies.R;
import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.network.MovieDbController;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.network.NetworkUtility;
import com.mobileallin.movies.screens.detail.activity.MovieActivity;
import com.mobileallin.movies.screens.home.AdapterMovies;
import com.mobileallin.movies.screens.home.MovieClickModule;
import com.mobileallin.movies.screens.home.activity.presenter.HomeActivityPresenter;
import com.mobileallin.movies.screens.home.activity.view.HomeActivityView;
import com.mobileallin.movies.utils.SortingCriteria;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class HomeActivity extends AppCompatActivity implements AdapterMovies.MovieOnItemClick, HomeActivityView {

    @BindView(R.id.all_movies_grid)
    RecyclerView listView;

    @BindView(R.id.home_view)
    ScrollView homeView;

    Call<APIResults<Movie>> reposCall;

    @Inject
    MovieService movieService;

    @Inject
    AdapterMovies adapterMovies;

    @Inject
    MovieDbController movieDbController;

    private SortingCriteria currentCriteria;

    private HomeActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        HomeActivityComponent component = DaggerHomeActivityComponent.builder()
                .homeActivityModule(new HomeActivityModule(this))
                .moviesApplicationComponent(MoviesApplication.get(this).component())
                .movieClickModule(new MovieClickModule(this))
                .build();

        component.injectHomeActivity(this);

        showLoadingIndicator();

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapterMovies);
/*
        movieDbController.getSortedMovies(reposCall, movieService,
                adapterMovies, this, homeView, SortingCriteria.MOST_POPULAR);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new HomeActivityPresenter(this);
        presenter.loadMovies(movieDbController, reposCall, movieService,
                adapterMovies, this, homeView, SortingCriteria.MOST_POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_popular:
                HomeActivity.this.showSortedData(SortingCriteria.MOST_POPULAR);
                return true;
            case R.id.sort_rated:
                HomeActivity.this.showSortedData(SortingCriteria.TOP_RATED);
                return true;
            case R.id.sort_favourite:
                HomeActivity.this.showSortedData(SortingCriteria.FAVORITE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLoadingIndicator() {
        homeView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    /**
     * Sort movies by given criteria
     */
    private void showSortedData(SortingCriteria criteria) {

       currentCriteria = presenter.selectSortedMovies(currentCriteria, criteria);

        if (currentCriteria == criteria) {
            return;
        }

        if (!NetworkUtility.isConnected(this)) {
            showErrorMessage(getResources().getString(R.string.no_connention));
            return;
        }

        currentCriteria = criteria;

        movieDbController.getSortedMovies(reposCall, movieService,
                adapterMovies, this, homeView, currentCriteria);

    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reposCall != null) {
            reposCall.cancel();
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.MOVIE_KEY, Parcels.wrap(movie));
        startActivity(intent);
    }


    @Override
    public void displayMovies(List<Movie> movies) {
        if (movies == null || movies.size() == 0) {
            Toast.makeText(this, "No favourite movies!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).exists()) movies.get(i).load();
        }
        adapterMovies.setMovies(movies);
    }

    @Override
    public void displayNoMovies() {

    }

    @Override
    public void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
