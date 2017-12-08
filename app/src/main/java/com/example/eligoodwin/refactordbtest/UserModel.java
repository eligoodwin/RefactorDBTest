package com.example.eligoodwin.refactordbtest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eligoodwin on 12/7/17.
 */

public class UserModel implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public UserModel createFromParcel(Parcel in){
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };


    private final String username;
    private final String tweet;
    private final String url;

    //constructors
    public UserModel(Parcel in){
        this.username = in.readString();
        this.url  = in.readString();
        this.tweet = in.readString();
    }

    public UserModel(String username, String url, String tweet){
        this.username = username;
        this.url = url;
        this.tweet = tweet;
    }


    public String getUsername() {
        return username;
    }

    public String getTweet() {
        return tweet;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.url);
        dest.writeString(this.tweet);
    }
}
