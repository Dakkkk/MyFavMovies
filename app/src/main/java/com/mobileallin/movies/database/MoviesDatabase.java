package com.mobileallin.movies.database;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;

/**
 * Database / dbFlow ContentProvider accrding to:
 * https://github.com/codepath/android_guides/wiki/DBFlow-Guide
 */

@ContentProvider(
        authority = MoviesDatabase.AUTHORITY,
        database = MoviesDatabase.class,
        baseContentUri = MoviesDatabase.BASE_CONTENT_URI
)
@Database(name = MoviesDatabase.DB_NAME, version = MoviesDatabase.DB_VERSION)
public class MoviesDatabase {
    static final String DB_NAME = "MoviesDatabase";
    static final int DB_VERSION = 1;

    static final String AUTHORITY = "com.mobileallin.movies";
    static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    /*  Movie endpoint declaration, ENDPOINT here*/
    @TableEndpoint(name = MovieProviderModel.ENDPOINT, contentProvider = MovieProviderModel.class)
    static class MovieProviderModel {

        public static final String ENDPOINT = "Movie";

        @ContentUri(path = MovieProviderModel.ENDPOINT,
                type = ContentUri.ContentType.VND_MULTIPLE + ENDPOINT)
        public static final Uri CONTENT_URI = buildUri(ENDPOINT);

     /* @ContentUri(
              path = ENDPOINT,
              type = ContentUri.ContentType.VND_MULTIPLE
      )
      public static final Uri MOVIE_URI = buildUri(ENDPOINT);

      @ContentUri(
              path = ENDPOINT + "/#",
              type = ContentUri.ContentType.VND_SINGLE,
              segments = {@ContentUri.PathSegment(segment = 1, column = "id")}
      )
      static Uri withID(int id) {
          return buildUri(ENDPOINT, Integer.toString(id));
      }*/
    }
}
