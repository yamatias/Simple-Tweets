package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.fragments.PageSliderFragment;
import com.codepath.apps.fragments.SearchFragment;
import com.codepath.apps.fragments.TopSearchSuggestionsFragment;
import com.codepath.apps.mysimpletweets.PostTweetActivity;
import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TweetsArrayAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity{

    final int REQUEST_CODE = 350;
    ImageView ivProfileImage3;
    TextView tvTitle;
    TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivProfileImage3 = (ImageView)findViewById(R.id.ivProfileImage3);

        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String url = response.getString("profile_image_url");
                    Picasso.with(TimelineActivity.this).load(url).fit().transform(new CircleTransform()).into(ivProfileImage3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug","Connection failed to get information");
            }
        });


        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("Home");


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        t.replace(R.id.flHomeTimelineContainer,new PageSliderFragment());
        t.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        final SearchView miSearch = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.miSearch));
//        final MenuItem miProfile = menu.findItem(R.id.miProfile);

        miSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(TimelineActivity.this, "You searched for " + query + "!", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t = fm.beginTransaction();

                //Creating SearchFragment and adding arguments to it
                SearchFragment sf = SearchFragment.newInstance(query,null);
                t.replace(R.id.flHomeTimelineContainer,sf);
                t.commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        miSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(TimelineActivity.this, "You clicked search!", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction t = fm.beginTransaction();

                t.replace(R.id.flHomeTimelineContainer,new TopSearchSuggestionsFragment());
                t.commit();
            }
        });

        miSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                onBackPressed();
                return true;
            }
        });



        miSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                Toast.makeText(SearchActivity.this, "Your focused changed!", Toast.LENGTH_SHORT).show();
//                miProfile.setVisible(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//        ProgressBar progressBar = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void onProfileView(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(TimelineActivity.this,R.anim.image_click));
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name",""); //Having "" as screen_name means you're looking at your own profile
        startActivity(i);
    }

    public void onPost(View view) {
        Intent intent = new Intent(this,PostTweetActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ListView lvTweets = (ListView)findViewById(R.id.lvTweets);

        TweetsArrayAdapter adapter =(TweetsArrayAdapter)lvTweets.getAdapter();


        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Tweet tweet = (Tweet)data.getExtras().getSerializable("tweet");
//            Log.d("debug","This is what tweet has: " + tweet.getUser().getName() + " " + tweet.getUser().getScreenName() + " " + tweet.getTimestamp());
            ArrayList<Tweet> newTweets = new ArrayList<>();
            for(int x = 0;x < adapter.getCount();x++) {
                newTweets.add(adapter.getItem(x));
            }

            adapter = new TweetsArrayAdapter(this,newTweets);

            lvTweets.setAdapter(adapter);
            newTweets.add(0,tweet);
            adapter.notifyDataSetChanged();

        }
    }

}
