package com.mobileallin.movies.models;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mobileallin.movies.database.MoviesDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.util.List;

/**
 * Movie model class
 */

@Table(database = MoviesDatabase.class)
@Parcel
public class Movie extends BaseModel implements Parcelable {

    /*
        Used for building poster URLs
     */
    private static final String MOVIE_DB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String DEFAULT_IMAGE_SIZE = "w500";

    /*
        JSON keys
     */
    private static final String ID_KEY = "id";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String POSTER_URL_KEY = "poster_path";
    private static final String PLOT_SYNOPSIS_KEY = "overview";
    private static final String USER_RATING_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String FAVOURITE = "favourite";

    @PrimaryKey(autoincrement = true)
    @Column
    @SerializedName(ID_KEY)
    public int id;

    @Column
    @SerializedName(ORIGINAL_TITLE_KEY)
    private String originalTitle;

    @Column
    @SerializedName(POSTER_URL_KEY)
    private String posterURL;

    @Column
    @SerializedName(PLOT_SYNOPSIS_KEY)
    private String plotSynopsis;

    @Column
    @SerializedName(USER_RATING_KEY)
    private double userRating;

    @Column
    @SerializedName(RELEASE_DATE_KEY)
    private String releaseDate;

    @Column
    @SerializedName(FAVOURITE)
    private boolean favourite;

    List<Video> videos;

    List<Review> reviews;

    List<Movie> favouriteMovies;


    public Movie() {
        // for DB
    }

    protected Movie(android.os.Parcel in) {
        id = in.readInt();
        originalTitle = in.readString();
        posterURL = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();
        favourite = (Boolean) in.readValue( null );
//        videos = in.createTypedArrayList(Video.CREATOR);
//        reviews = in.createTypedArrayList(Review.CREATOR);
        favouriteMovies = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(android.os.Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getFullPosterURL() {
        return MOVIE_DB_POSTER_BASE_URL + DEFAULT_IMAGE_SIZE + posterURL;
    }

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.getId());
        parcel.writeString(this.getOriginalTitle());
        parcel.writeString(this.getFullPosterURL());
        parcel.writeString(this.getPlotSynopsis());
        parcel.writeDouble(this.getUserRating());
        parcel.writeString(this.getReleaseDate());
        parcel.writeValue(this.isFavourite());
        parcel.writeList(this.getVideos());
        parcel.writeList(this.getReviews());
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        videos.forEach(v -> v.setMovie(this));
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "reviews")
    public List<Review> getReviews() {
    /*    if (reviews == null) {
            reviews = SQLite.select()
                    .from(Review.class)
                    .where(Review_Table.movie_id.is(id))
                    .queryList();
        }
        return reviews;*/
        return null;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "videos")
    public List<Video> getVideos() {
       /* if (videos == null) {
            videos = SQLite.select()
                    .from(Video.class)
                    .where(Video_Table.movie_id.is(id))
                    .queryList();
        }
        return videos;*/
        return null;
    }
}
