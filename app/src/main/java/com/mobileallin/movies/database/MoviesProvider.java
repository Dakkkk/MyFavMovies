package com.mobileallin.movies.database;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;

/**
 * Created by Dawid on 2017-09-20.
 */
/*dbFlow Content Provider needs authority, database, baseContentUri*/

@ContentProvider(
        authority = MoviesProvider.AUTHORITY,
        database = MoviesDatabase.class,
        baseContentUri = MoviesProvider.BASE_CONTENT_URI
)

public class MoviesProvider {
    static final String AUTHORITY = "com.mobileallin.movies";

    static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(name = MoviesProviderModel.ENDPOINT, contentProvider = MoviesProvider.class)
    static class MoviesProviderModel {

        static final String ENDPOINT = "Movie";

        @ContentUri(
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
        }
    }
}
