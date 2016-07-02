package com.codepath.apps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchFragment extends TweetsListFragment {

    private String query;
    private String resultType;

    TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // keep this and add to it!

        //Check if there's a Bundle of stuff - the query must be in here, but result_type is optional
        query = getArguments().getString("query");
        if(getArguments().getString(resultType) == null) {
            resultType = "popular";
        }
        else {
            resultType = getArguments().getString("result_type");
        }

        populateTimeline();
    }


    @Override
    public void populateTimeline() {
        showProgressBar();
        client = TwitterApplication.getRestClient();
        client.getQuery(query, resultType,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Toast.makeText(getContext(), "You searched for Trump!", Toast.LENGTH_SHORT).show();
                Log.d("debug","The jsonobject response is: " + response.toString());
                try {
                    JSONArray statuses = response.getJSONArray("statuses");
                    if(statuses.length() == 0) {
                        Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                    }
                    addAll(Tweet.fromJSONArray(statuses));
                    Log.d("debug","the adapter is:" + adapter.toString());
                } catch(JSONException e) {
                    e.printStackTrace();
                    Log.d("debug","statuses tag not found");
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug","Connection failed with jsonobject");
            }
        });
    }

    public static SearchFragment newInstance(String query, String resultType) {

        Bundle args = new Bundle();
        args.putString("query",query);
        if (resultType != null) {
            args.putString("result_type",resultType);
        }

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
