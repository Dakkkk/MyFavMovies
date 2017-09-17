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
public class Video extends BaseModel {

    /*
        JSON KEYS
     */
    private static final String ID_KEY = "id";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String SITE_KEY = "site";
    private static final String SIZE_KEY = "size";
    private static final String TYPE_KEY = "type";

    @PrimaryKey
    @Column
    @SerializedName(ID_KEY)
    private String id;

    @Column
    @SerializedName(KEY_KEY)
    private String key;

    @Column
    @SerializedName(NAME_KEY)
    private String name;

    @Column
    @SerializedName(SITE_KEY)
    private String site;

    @Column
    @SerializedName(SIZE_KEY)
    private int size;

    @Column
    @SerializedName(TYPE_KEY)
    private String type;

    @ForeignKey(stubbedRelationship = true)
    private Movie movie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
    // --------------------------------------------------------------------------------------

 /*   @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }

    protected Video(Parcel in) {
        this.id = in.readString();
        this.iso = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public static final class Response {

        @Expose
        public long id;

        @Expose
        @SerializedName("results")
        public List<Video> videos;

    }
}*/
