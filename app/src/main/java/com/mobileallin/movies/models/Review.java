/*
 * Copyright 2015.  Emin Yahyayev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mobileallin.movies.models;

import com.google.gson.annotations.SerializedName;
import com.mobileallin.movies.database.MoviesDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


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
}


/*
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
}*/
