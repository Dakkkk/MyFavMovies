package com.mobileallin.movies.screens.detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileallin.movies.R;
import com.mobileallin.movies.models.Review;
import com.mobileallin.movies.screens.detail.activity.MovieActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Dawid on 2017-09-19.
 */

public class AdapterDetailReviews extends RecyclerView.Adapter<AdapterDetailReviews.DetailViewHolder> {

    private Context mContext;
    private List<Review> reviewsList = new ArrayList<>(0);

    @Inject
    public AdapterDetailReviews(MovieActivity context) {
        this.mContext = context;
    }

    public void setMovies(List<Review> reviews) {
        this.reviewsList = reviews;
        notifyDataSetChanged();
    }


    @Override
    public AdapterDetailReviews.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.detail_reviews_item, parent, false);
        return new AdapterDetailReviews.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.setReview(reviewsList.get(position));
    }


    @Override
    public int getItemCount() {
        return reviewsList == null ? 0 : reviewsList.size();
    }

    public void swapData(Collection<Review> movieReviews) {
        if (movieReviews == null) return;
        reviewsList.clear();
        if (movieReviews != null) {
            reviewsList.addAll(movieReviews);
        }
        notifyDataSetChanged();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {

        private TextView mReviewAuthorTextView;
        private TextView mReviewContentTextView;

        DetailViewHolder(View itemView) {
            super(itemView);
            mReviewAuthorTextView = itemView.findViewById(R.id.detail_review_author);
            mReviewContentTextView = itemView.findViewById(R.id.detail_review_content);
        }

        void setReview(Review review) {
            mReviewAuthorTextView.setText(review.getAuthor());
            mReviewContentTextView.setText(review.getContent());
        }
    }
}
