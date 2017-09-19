package com.mobileallin.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobileallin.movies.database.MoviesDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;


@Table(database = MoviesDatabase.class)
@org.parceler.Parcel
public class Review extends BaseModel {

    /*
        JSON KEYS
     */
    private static final String ID_KEY = "id";
    private static final String AUTHOR_KEY = "author";
    private static final String CONTENT_KEY = "content";
    private static final String URL_KEY = "url";

    public Review(){}

    @PrimaryKey
    @Column
    @SerializedName(ID_KEY)
    private String id;

    @Column
    @SerializedName(AUTHOR_KEY)
    private String author;

    @Column
    @SerializedName(CONTENT_KEY)
    private String content;

    @Column
    @SerializedName(URL_KEY)
    private String url;

    @ForeignKey(stubbedRelationship = true)
    private Movie movie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    protected Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };


public static final class Response {

        @Expose
        public long id;

        @Expose
        public int page;

        @Expose
        @SerializedName("results")
        public List<Review> reviews;

        @Expose
        @SerializedName("total_pages")
        public int totalPages;

        @Expose
        @SerializedName("total_results")
        public int totalResults;

    }
}
