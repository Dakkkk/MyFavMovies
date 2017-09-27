package com.mobileallin.movies.screens.detail.adapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 2017-09-19.
 */
@Module
public class VideoClickModule {
    private final AdapterDetailVideos.VideoOnClickHandler videoClick;

    public VideoClickModule(AdapterDetailVideos.VideoOnClickHandler click) {
        this.videoClick = click;
    }

    @Provides
    public AdapterDetailVideos.VideoOnClickHandler videoClick() {
        return videoClick;
    }
}
