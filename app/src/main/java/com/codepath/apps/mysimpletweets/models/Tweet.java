package com.codepath.apps.mysimpletweets.models;

import android.util.Log;

import com.codepath.apps.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Class parses JSON and stores data, then encapsulates any logic about the data
public class Tweet implements Serializable {

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    //List out attributes of Tweet that are important to us
    private String body;
    private long id;
    private User user;
    private String createdAt;
    private String timestamp;
    private String imageMediaLink;

    public String getImageMediaLink() {
        return imageMediaLink;
    }

    public Tweet() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            //Log.d("debug","Here is the JSONObject: "+jsonObject.toString());
            tweet.body = jsonObject.getString("text");
            tweet.id = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
//            tweet.timestamp = getRelativeTimeAgo(jsonObject.getString("created_at"));
            tweet.timestamp = TimeFormatter.getTimeDifference(jsonObject.getString("created_at"));


            try {
                tweet.imageMediaLink = jsonObject.getJSONObject("extended_entities").getJSONArray("media").getJSONObject(0).getString("media_url_https"); //might cause problems
                Log.d("debug", "tweet.medialink is: " + tweet.imageMediaLink);
            } catch(JSONException e) {
                tweet.imageMediaLink = "";
            }


        } catch(JSONException e) {
            e.printStackTrace();
            Log.d("debug","Couldn't get the JSON parsing to work");
        }

        return tweet;
    }

    public static List<Tweet> fromJSONArray(JSONArray jsonArray)
    {
        ArrayList<Tweet> tweetArrayList = new ArrayList<>();
        for(int x=0;x<jsonArray.length();x++) {

            try {
                tweetArrayList.add(Tweet.fromJSON((JSONObject) jsonArray.get(x)));
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweetArrayList;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeSerializable(this.user);
//        dest.writeString(this.body);
//        dest.writeLong(this.id);
//        dest.writeString(this.createdAt);
//        dest.writeString(this.timestamp);
//        dest.writeString(this.imageMediaLink);
//    }
//
//    protected Tweet(Parcel in) {
//        this.user = (User)in.readSerializable(); //FIX THIS
//        this.body = in.readString();
//        this.id = in.readLong();
//        this.createdAt = in.readString();
//        this.timestamp = in.readString();
//        this.imageMediaLink = in.readString();
//    }
//
//    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
//        @Override
//        public Tweet createFromParcel(Parcel source) {
//            return new Tweet(source);
//        }
//
//        @Override
//        public Tweet[] newArray(int size) {
//            return new Tweet[size];
//        }
//    };
}