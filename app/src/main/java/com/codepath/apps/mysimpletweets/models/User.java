package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by matiash on 6/27/16.
 */
public class User implements Serializable {

    public User() {

    }
    private String name;
    private long id;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followers;
    private int followings;

    public int getFollowers() {
        return followers;
    }

    public int getFollowings() {
        return followings;
    }

    public String getTagline() {
        return tagline;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.id = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.tagline = jsonObject.getString("description");
            user.followers = jsonObject.getInt("followers_count");
            user.followings = jsonObject.getInt("friends_count");
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(name);
//        parcel.writeLong(id);
//        parcel.writeString(screenName);
//        parcel.writeString(profileImageUrl);
//        parcel.writeString(tagline);
//        parcel.writeInt(followers);
//        parcel.writeInt(followings);
//
//    }
//
//    public static final Parcelable.Creator<User> CREATOR
//            = new Parcelable.Creator<User>() {
//
//        // This simply calls our new constructor (typically private) and
//        // passes along the unmarshalled `Parcel`, and then returns the new object!
//        @Override
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        // We just need to copy this and change the type to match our class.
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.name);
//        dest.writeLong(this.id);
//        dest.writeString(this.screenName);
//        dest.writeString(this.profileImageUrl);
//        dest.writeString(this.tagline);
//        dest.writeInt(this.followers);
//        dest.writeInt(this.followings);
//    }
//
//    protected User(Parcel in) {
//        this.name = in.readString();
//        this.id = in.readLong();
//        this.screenName = in.readString();
//        this.profileImageUrl = in.readString();
//        this.tagline = in.readString();
//        this.followers = in.readInt();
//        this.followings = in.readInt();
//    }
//
//    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel source) {
//            return new User(source);
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
}
