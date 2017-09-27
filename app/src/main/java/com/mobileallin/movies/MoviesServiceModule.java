package com.mobileallin.movies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobileallin.movies.network.DateTimeConverter;
import com.mobileallin.movies.network.MovieService;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class MoviesServiceModule {

    @Provides
    @MoviesApplicationScope
    public MovieService githubService(Retrofit gitHubRetrofit) {
        return gitHubRetrofit.create(MovieService.class);
    }

    @Provides
    @MoviesApplicationScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    @MoviesApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
