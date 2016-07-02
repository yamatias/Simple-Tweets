package com.codepath.apps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Trend;
import com.codepath.apps.mysimpletweets.models.TrendArrayAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TopSearchSuggestionsFragment extends Fragment{
    ArrayList<Trend> trends;
    TrendArrayAdapter adapter;
    ListView lvTrends;
    TwitterClient client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trends = new ArrayList<>();
        adapter = new TrendArrayAdapter(getContext(),trends);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends,container,false);
        lvTrends = (ListView)view.findViewById(R.id.lvTrends);
        lvTrends.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client = TwitterApplication.getRestClient();
        client.getTrendsList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //get json, fill adapter with Trends list

                //going to turn jsonarray into jsonobject
                JSONObject responseObject;
//                Log.d("debug","response is: "+response.toString());
                try {
                    responseObject = (JSONObject) response.get(0);
                } catch(JSONException e) {
                    e.printStackTrace();
//                    Log.d("debug","exception encountered");
                    responseObject = new JSONObject(); //will give errors
                }
                adapter.addAll(Trend.fromJSONArray(responseObject));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("debug","connection failed. " +errorResponse.toString());
            }
        });
    }
}
