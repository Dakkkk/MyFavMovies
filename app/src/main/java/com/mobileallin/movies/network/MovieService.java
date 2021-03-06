package com.mobileallin.movies.network;


import com.mobileallin.movies.data.APIResults;
import com.mobileallin.movies.models.Movie;
import com.mobileallin.movies.models.Review;
import com.mobileallin.movies.models.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {
    String ENDPOINT = "https://api.themoviedb.org/3/movie/";

    @GET("popular")
    Call<APIResults<Movie>> getMostPopular();

    @GET("top_rated")
    Call<APIResults<Movie>> getTopRated();

    @GET("{id}/videos")
    Call<APIResults<Video>> getVideos(@Path("id") int movieID);

    @GET("{id}/reviews")
    Call<APIResults<Review>> getReviews(@Path("id") int movieID);
}
