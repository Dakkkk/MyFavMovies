package com.mobileallin.movies.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Database
 */
@Database(name = MoviesDatabase.DB_NAME, version = MoviesDatabase.DB_VERSION)
public class MoviesDatabase {
    static final String DB_NAME = "MoviesDatabase";
    static final int DB_VERSION = 1;
}
