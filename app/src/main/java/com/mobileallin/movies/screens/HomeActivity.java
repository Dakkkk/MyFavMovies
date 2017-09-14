package com.mobileallin.movies.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mobileallin.movies.MoviesApplication;
import com.mobileallin.movies.R;
import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.network.MovieService;
import com.mobileallin.movies.screens.home.AdapterMovies;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.all_movies_grid)
    RecyclerView listView;

    Call<APIResults<Movie>> reposCall;

    @Inject
    MovieService movieService;

    @Inject
    AdapterMovies adapterMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HomeActivityComponent component = DaggerHomeActivityComponent.builder()
                .homeActivityModule(new HomeActivityModule(this))
                .moviesApplicationComponent(MoviesApplication.get(this).component())
                .build();

        component.injectHomeActivity(this);

        // listView.setAdapter(adapterMovies);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapterMovies);


        reposCall = movieService.getMostPopular();
        reposCall.enqueue(new Callback<APIResults<Movie>>() {
            @Override
            public void onResponse(Call<APIResults<Movie>> call, Response<APIResults<Movie>> response) {
                Log.i("API response", response.body().results.get(0).toString());
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
    protected void onDestroy() {
        super.onDestroy();
        if (reposCall != null) {
            reposCall.cancel();
        }
    }
}
