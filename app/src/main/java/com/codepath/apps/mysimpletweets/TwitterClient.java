package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a Twitter API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "QxqWK1CoW1ZjVrSdL2pjxSVP2";       // Change this
	public static final String REST_CONSUMER_SECRET = "RDnClThtMrvaacxKbp3o8VFCr1oN4YtN0EP5MZQZb8liA5A05y"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://twittertweetsclientapplication"; // Change this (here and in manifest)

	public static final int WOEID = 23424977; //change this to whatever location you want to search for trends. 23424977 is for USA

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	//Gotta make get the endpoints using the Twitter API in the TwitterClient
	//Handler's behavior will be defined later and not in this method
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("since_id",1); //creates unfiltered list of most recent tweets
		client.get(apiUrl,params, handler);

	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",25);
		client.get(apiUrl,params, handler);

	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("screen_name",screenName);
		client.get(apiUrl,params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl,null, handler);
	}

	public void getOtherUserInfo(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name",screenName);
		client.get(apiUrl,params, handler);
	}

	public void postTweet(String text, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status",text);
		client.post(apiUrl,params,handler);
	}

	public void getTrendsList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("trends/place.json");
		RequestParams params = new RequestParams();
		params.put("id",WOEID);
		client.get(apiUrl,params,handler);
	}

	public void getQuery(String q, String resultType, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("q",q);
		params.put("result_type",resultType);
		client.get(apiUrl,params,handler);
	}


}