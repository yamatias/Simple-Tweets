package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matiash on 6/29/16.
 */
public class Trend implements Parcelable {
    String topic;
    int tweetVolume;
    String url;

    public String getUrl() {
        return url;
    }

    public String getTopic() {
        return topic;
    }

    public int getTweetVolume() {
        return tweetVolume;
    }

    public static Trend fromJSON(JSONObject jsonObject) {
        Trend trend = new Trend();
        try {
            trend.topic = jsonObject.getString("name");
            trend.tweetVolume = jsonObject.getInt("tweet_volume");
            trend.url = jsonObject.getString("url");
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return trend;
    }

    public static List<Trend> fromJSONArray(JSONObject response) {
        ArrayList<Trend> trends = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = response.getJSONArray("trends");
        } catch (JSONException e) {
            e.printStackTrace();
            jsonArray = new JSONArray(); //this will give errors
        }

        for(int x =0;x < jsonArray.length();x++) {
            Trend trend = new Trend();
            try {
                JSONObject jsonObject = (JSONObject)jsonArray.get(x);
                trend.topic = jsonObject.getString("name");

                try {
                    trend.tweetVolume = jsonObject.getInt("tweet_volume");
                }
                catch (JSONException e) {
                    trend.tweetVolume = -1;
                }

                trend.url = jsonObject.getString("url");
            } catch(JSONException e) {
                e.printStackTrace();
                continue;
            }

            trends.add(trend);
        }

        return trends;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.topic);
        dest.writeInt(this.tweetVolume);
        dest.writeString(this.url);
    }

    public Trend() {
    }

    protected Trend(Parcel in) {
        this.topic = in.readString();
        this.tweetVolume = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Trend> CREATOR = new Parcelable.Creator<Trend>() {
        @Override
        public Trend createFromParcel(Parcel source) {
            return new Trend(source);
        }

        @Override
        public Trend[] newArray(int size) {
            return new Trend[size];
        }
    };
}
