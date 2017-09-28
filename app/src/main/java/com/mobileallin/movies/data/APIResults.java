package com.mobileallin.movies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used for formatting the response returned from the API.
 */

public class APIResults<T> {
    private static final String API_RESULTS_KEY = "results";

    @SerializedName(API_RESULTS_KEY)
    public List<T> results;
}
