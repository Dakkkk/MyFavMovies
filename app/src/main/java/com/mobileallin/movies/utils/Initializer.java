package com.mobileallin.movies.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Dawid on 2017-09-15.
 */

/*
Initialize database and leakcanary
*/

public class Initializer {
    public static void init(@NonNull Application application) {
        FlowManager.init(new FlowConfig.Builder(application).build());

        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
    }

    public static void initDb(@NonNull Context application) {
        FlowManager.init(new FlowConfig.Builder(application).build());
    }
}

