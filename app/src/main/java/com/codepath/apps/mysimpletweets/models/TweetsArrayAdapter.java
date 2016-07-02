package com.codepath.apps.mysimpletweets.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.PostTweetActivity;
import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

//Takes the Tweets Objects and turning them into views
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1,tweets);
    }

    //Override and set custom template
    User user;
    Tweet tweet;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get Tweet, then find/inflate template, find subviews to fill with data, then populate data into subviews, return finished view
        tweet = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }

        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        final TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvTimestamp = (TextView)convertView.findViewById(R.id.tvTimestamp);
        final TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        ImageView ivMediaImage= (ImageView)convertView.findViewById(R.id.ivMediaImage);
        ImageButton ibReply = (ImageButton)convertView.findViewById(R.id.ibReply);

        user = tweet.getUser();
        tvName.setText(user.getName());
//        Log.d("debug","Name is:" + user.getName());
        tvUsername.setText("@"+user.getScreenName());
//        Log.d("debug","screenName is:" + user.getScreenName());
        tvBody.setText(tweet.getBody());
        tvTimestamp.setText(tweet.getTimestamp());
//        Log.d("debug","Timestamp is:" + tweet.getTimestamp());

        ivMediaImage.setImageResource(0);
        ivMediaImage.setVisibility(View.VISIBLE);
        if(tweet.getImageMediaLink().equals("")) {
                ivMediaImage.setVisibility(View.GONE);
        }
        else {
            Picasso.with(getContext()).load(tweet.getImageMediaLink()).fit().into(ivMediaImage);
        }


        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.image_click));
//                Toast.makeText(getContext(), "You touched reply!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), PostTweetActivity.class);
                intent.putExtra("screen_name",tvUsername.getText().toString());
                getContext().startActivity(intent);
            }
        });

        ivProfileImage.setImageResource(android.R.color.transparent); //resets the imageview
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(4, 4)).into(ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.image_click));
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name",tvUsername.getText().toString());
                getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
