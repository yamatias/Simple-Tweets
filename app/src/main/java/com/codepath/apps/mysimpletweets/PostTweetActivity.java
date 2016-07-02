package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PostTweetActivity extends AppCompatActivity {
    //TODO detail view of tweet, profile icon is your icon and set it to the top left corner, replace home and mentions with icons (@)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); //auto pulls up the keyboard

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);

        final Button bnTweet = (Button)findViewById(R.id.bnTweet);
        final EditText etText = (EditText)findViewById(R.id.etText);
        final TextView tvCharCount = (TextView)findViewById(R.id.tvCharCount);
        final int GRAY = tvCharCount.getCurrentTextColor();

        if(getIntent().getStringExtra("screen_name") != null) {
            etText.setText(getIntent().getStringExtra("screen_name"));
        }
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                //Update tvCharCount textview with remaining character left
                int charsLeft = 140-editable.length();
                tvCharCount.setText(charsLeft+"");

                if(charsLeft == -1){
                    tvCharCount.setTextColor(Color.RED);
                    bnTweet.setClickable(false); //maybe try to change this with the styles if you have time
//                    bnTweet.setBackgroundColor();
                } else if(charsLeft == 0){
                    tvCharCount.setTextColor(GRAY);
                    bnTweet.setClickable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}


        });
        bnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(PostTweetActivity.this,R.anim.image_click));
                String text = etText.getText().toString();
//                Toast.makeText(PostTweetActivity.this, "You sent: " + text, Toast.LENGTH_SHORT).show();
                TwitterClient client = TwitterApplication.getRestClient();
                client.postTweet(text, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        Toast.makeText(PostTweetActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                        Log.d("debug","JSON response is: " + response.toString());
                        Tweet tweet = Tweet.fromJSON(response);
//                        Log.d("debug","Tweet User Name, ScreenName, and Timestamp should be: " + tweet.getUser().getName() + " " + tweet.getUser().getScreenName() + " " + tweet.getTimestamp());
                        Intent data = new Intent();
                        data.putExtra("tweet",tweet);
                        setResult(RESULT_OK,data);
                        finish();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("debug","failed to connect to network");
                        Log.d("debug",errorResponse.toString());
                    }
                });
            }
        });
    }

    public void onExitClick(View view) {
        finish();
    }
}
