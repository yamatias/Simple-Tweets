package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.fragments.ProfileHeaderFragment;
import com.codepath.apps.fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getStringExtra("screen_name");


        if(savedInstanceState == null) {
            //Create User Header Fragment
            ProfileHeaderFragment fragmentProfileHeader = ProfileHeaderFragment.newInstance(screenName);

            //Create User Fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            //Display user fragment
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.flContainer, fragmentUserTimeline);
            transaction.replace(R.id.flHeaderContainer,fragmentProfileHeader);
            transaction.commit();
        }
    }
}
