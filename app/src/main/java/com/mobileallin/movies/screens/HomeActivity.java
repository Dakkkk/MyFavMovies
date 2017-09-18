package com.mobileallin.movies.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileallin.movies.MoviesApplication;
import com.mobileallin.movies.R;
import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.models.Movie_Table;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.screens.detail.MovieActivity;
import com.mobileallin.movies.screens.home.AdapterMovies;
import com.mobileallin.movies.screens.home.MovieClickModule;
import com.mobileallin.movies.utils.SortingCriteria;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements AdapterMovies.MovieOnItemClick {

    @BindView(R.id.all_movies_grid)
    RecyclerView listView;

    Call<APIResults<Movie>> reposCall;

    @Inject
    MovieService movieService;

    @Inject
    AdapterMovies adapterMovies;

    private SortingCriteria currentCriteria;

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

/*
        Initializer.init(this.getApplication());
*/


        // listView.setAdapter(adapterMovies);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapterMovies);


        reposCall = movieService.getMostPopular();
        reposCall.enqueue(new Callback<APIResults<Movie>>() {
            @Override
            public void onResponse(Call<APIResults<Movie>> call, Response<APIResults<Movie>> response) {
                Log.i("API response", response.body().results.get(2).getFullPosterURL());
                adapterMovies.swapData(response.body().results);
            }

            @Override
            public void onFailure(Call<APIResults<Movie>> call, Throwable t) {
                Log.e("API failure", t.getMessage());
                Toast.makeText(HomeActivity.this, "Error getting movies " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAdequateMovies(SortingCriteria sortingCriteria) {
        reposCall = movieService.getMostPopular();
        if (sortingCriteria == SortingCriteria.FAVORITE) {
            reposCall = movieService.getMostPopular();
        } else if (sortingCriteria == SortingCriteria.TOP_RATED) {
            reposCall = movieService.getTopRated();
        }
        reposCall.enqueue(new Callback<APIResults<Movie>>() {
            @Override
            public void onResponse(Call<APIResults<Movie>> call, Response<APIResults<Movie>> response) {
                Log.i("API response", response.body().results.get(2).getFullPosterURL());
                adapterMovies.swapData(response.body().results);
            }

            @Override
            public void onFailure(Call<APIResults<Movie>> call, Throwable t) {
                Log.e("API failure", t.getMessage());
                Toast.makeText(HomeActivity.this, "Error getting movies " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    /**
     * Sort movies by given criteria
     */


    /**
     * Sort movies by given criteria
     */
    private void showSortedData(SortingCriteria criteria) {
        if (currentCriteria == criteria) {
            return;
        }

        if (criteria == SortingCriteria.FAVORITE) {
            currentCriteria = criteria;
            SQLite.select()
                    .from(Movie.class)
                    .where(Movie_Table.favourite.is(true))
                    .async()
                    .queryListResultCallback((__, movies) -> setMovies(movies))
                    .execute();
            return;
        }
/*
        if (!NetworkUtility.isConnected(this)) {
            showErrorMessage(getResources().getString(R.string.no_connention));
            return;
        }*/

        currentCriteria = criteria;

        getAdequateMovies(currentCriteria);

    }


    //ToDo stream requres java 8!
    private void setMovies(List<Movie> movies) {
        if (movies == null) return;
        Log.d("setMovies, fav", movies.toString());
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).exists()) movies.get(i).load();
        }
        //movies.stream().filter(Movie::exists).forEach(Movie::load);
        adapterMovies.setMovies(movies);
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
        Log.i("Click", "Clicked");
        startActivity(intent);
    }
}
