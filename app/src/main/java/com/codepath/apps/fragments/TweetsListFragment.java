package com.codepath.apps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.OnProgressListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TweetsArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment implements OnProgressListener{

    ArrayList<Tweet> tweets;
    TweetsArrayAdapter adapter;
    ListView lvTweets;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;
//    OnProgressListener listener;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        adapter = new TweetsArrayAdapter(getActivity(),tweets);


    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof OnProgressListener) {
//            listener = (OnProgressListener)context;
//        }
//        else {
//            Log.d("debug","OnProgressListener not implemented in class");
//        }
//    }

    public void showProgressBar() {
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if(progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list,container,false);
        lvTweets = (ListView)view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressBar();
                populateTimeline();
                swipeContainer.setRefreshing(false); //might not be what I want
                hideProgressBar();
            }
        });



        return view;
    }

    public void addAll(List<Tweet> tweets) {
        adapter.clear();
        adapter.addAll(tweets);
    }

    public abstract void populateTimeline(); //Will be overidden by subclasses
}
