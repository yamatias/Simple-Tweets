package com.codepath.apps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileHeaderFragment extends Fragment {
    private TwitterClient client;
    TextView tvName;
    TextView tvFollowers;
    TextView tvFollowing;
    ImageView ivImage;
    TextView tvTagline;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateProfileHeader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvFollowers = (TextView)view.findViewById(R.id.tvFollowers);
        tvFollowing = (TextView)view.findViewById(R.id.tvFollowing);
        ivImage = (ImageView)view.findViewById(R.id.ivImage);
        tvTagline = (TextView)view.findViewById(R.id.textView2);


        return view;
    }

    private void populateProfileHeader() {
        String screenName = getArguments().getString("screen_name");

        //If screenName is "", means user is accessing his own profile
        if(screenName.equals("")) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON(response);
                    fillInformation(user);
                }
            });
        }

        //Else the user is looking at another person's profile
        else {
            client.getOtherUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON(response);
                    fillInformation(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("debug", "failed to connect");
                }
            });
        }
    }

    public static ProfileHeaderFragment newInstance(String screenName) {

        Bundle args = new Bundle();
        args.putString("screen_name",screenName);
        ProfileHeaderFragment fragment = new ProfileHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void fillInformation(User user) {
        if(user.getProfileImageUrl() != null) {
            Picasso.with(getContext()).load(user.getProfileImageUrl()).transform(new RoundedCornersTransformation(4, 4)).into(ivImage);
        }
        tvName.setText(user.getName());
        if(user.getTagline() == null || user.getTagline().equals(""))
            tvTagline.setText("");
        else
            tvTagline.setText(user.getTagline());

        tvFollowers.setText(user.getFollowers()+" Followers");
        tvFollowing.setText(user.getFollowings()+" Following");
    }
}
