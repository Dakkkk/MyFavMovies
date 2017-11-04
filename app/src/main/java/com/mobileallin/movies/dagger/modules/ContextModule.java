package com.mobileallin.movies.dagger.modules;

import android.content.Context;

import com.mobileallin.movies.ApplicationContext;
import com.mobileallin.movies.dagger.MoviesApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @MoviesApplicationScope
    @ApplicationContext
    public Context context() {
        return context;
    }
}
