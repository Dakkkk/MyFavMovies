package com.mobileallin.movies.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobileallin.movies.BuildConfig;
import com.mobileallin.movies.data.APIResults;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract controller for getting movies from endpoint
 */
public class TheMovieDBController<R> implements Callback<APIResults<R>> {

    private static final MovieService API = createAPI();

    private Function<MovieService, Call<APIResults<R>>> caller;
    private Consumer<List<R>> consumer;

    private Consumer<Void> onCall;
    private Consumer<Void> onSuccess;
    private Consumer<Throwable> onError;

    public TheMovieDBController(
            @NonNull Function<MovieService, Call<APIResults<R>>> caller,
            @NonNull Consumer<List<R>> consumer,
            Consumer<Void> onCall,
            Consumer<Void> onSuccess,
            Consumer<Throwable> onError
    ) {
        this.caller = caller;
        this.consumer = consumer;
        this.onCall = onCall;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    public TheMovieDBController(
            Function<MovieService, Call<APIResults<R>>> caller,
            Consumer<List<R>> consumer
    ) {
        this(caller, consumer, null, null, null);
    }

    public void getResults() {
        if (onCall != null) {
            onCall.accept(null);
        }
        Call<APIResults<R>> call = caller.apply(API);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<APIResults<R>> call, Response<APIResults<R>> response) {
        if (response.isSuccessful()) {
            if (onSuccess != null) {
                onSuccess.accept(null);
            }
            consumer.accept(response.body().results);
        } else {
            onFailure(call, new Exception("Request failed"));
        }
    }

    @Override
    public void onFailure(Call<APIResults<R>> call, Throwable t) {
        if (onError != null) {
            onError.accept(t);
        }
    }

    /**
     * Create API endpoint
     *
     * @return API
     */
    private static MovieService createAPI() {
        Log.i("APIcall start", "createAPI");
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request request = chain.request();
                            HttpUrl url = request.url().newBuilder()
                                    .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                                    .addQueryParameter("language", "en-US")
                                    .addQueryParameter("page", "1")
                                    .build();
                            request = request.newBuilder().url(url).build();
                            return chain.proceed(request);
                        }
                ).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.i("APIcall retrofit", "createAPI");

        return retrofit.create(MovieService.class);
    }
}
