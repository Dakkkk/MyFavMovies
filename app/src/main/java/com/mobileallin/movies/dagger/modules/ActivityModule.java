package com.mobileallin.movies.dagger.modules;

import android.app.Activity;
import android.content.Context;

import com.mobileallin.movies.dagger.MoviesApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity context;

    public ActivityModule(Activity context) {
        this.context = context;
    }

    @Provides
    @MoviesApplicationScope
    @Named("activity_context")
    public Context context() {
        return context;
    }
}
