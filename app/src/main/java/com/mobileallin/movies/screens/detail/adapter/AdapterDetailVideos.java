package com.mobileallin.movies.screens.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileallin.movies.R;
import com.mobileallin.movies.models.Video;
import com.mobileallin.movies.screens.detail.MovieActivity;
import com.mobileallin.movies.screens.home.AdapterMovies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Dawid on 2017-09-19.
 */

public class AdapterDetailVideos extends RecyclerView.Adapter<AdapterDetailVideos.DetailViewHolder> {

    private Context mContext;
    private List<Video> videosList = new ArrayList<>(0);

    public AdapterMovies.MovieOnItemClick mOnMovieClick;

    private VideoOnClickHandler mClickHandler;

    public interface VideoOnClickHandler {
        void onClick(Video video);
    }


    @Inject
    public AdapterDetailVideos(MovieActivity context, VideoOnClickHandler clickHandler) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }


    public AdapterDetailVideos(@NonNull Context context) {
        this.mContext = context;
    }

    public void setVideos(List<Video> videos) {
        this.videosList = videos;
        notifyDataSetChanged();
    }


    @Override
    public AdapterDetailVideos.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.detail_videos_item, parent, false);
        return new AdapterDetailVideos.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.setVideo(videosList.get(position));
    }


    @Override
    public int getItemCount() {
        return videosList == null ? 0 : videosList.size();
    }

    public void swapData(Collection<Video> movieVideos) {
        if (movieVideos == null) return;
        videosList.clear();
        if (movieVideos != null) {
            videosList.addAll(movieVideos);
        }
        notifyDataSetChanged();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mVideoAuthorTextView;
/*
        private VideoView mVideoView;
*/
        private TextView mVideoUrlTextView;

        DetailViewHolder(View itemView) {
            super(itemView);
            mVideoAuthorTextView = itemView.findViewById(R.id.detail_video_title);
/*
            mVideoView = itemView.findViewById(R.id.detail_video);
*/
            mVideoUrlTextView = itemView.findViewById(R.id.detail_video_url);
            itemView.findViewById(R.id.video_share).setOnClickListener(this);
            itemView.findViewById(R.id.video_video).setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        void setVideo(Video video) {
            mVideoUrlTextView.setText(video.getName());
/*
            mVideoView.setVideoPath(video.getSite());
*/
            mVideoAuthorTextView.setText(video.getSite());

            Log.i("img", video.getSite());
        }

        @Override
        public void onClick(View view) {
            if (mClickHandler != null) {
                Video video = videosList.get(getAdapterPosition());
                boolean share = view.getId() == R.id.video_share;
                mClickHandler.onClick(video);
            }
        }
    }

}
