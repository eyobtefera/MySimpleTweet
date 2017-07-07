package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    TwitterClient client;
    String screenName;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        screenName = getIntent().getExtras().getString("search_tweet");
        client = TwitterApp.getRestClient();
        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets);
        RecyclerView rvTweets = (RecyclerView) findViewById(R.id.searchTweets);
        rvTweets.setAdapter(tweetAdapter);

        rvTweets.setLayoutManager(new LinearLayoutManager(this));

        client.searchTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray statuses = response.getJSONArray("statuses");
                    for(int i =0; i<statuses.length(); i++)
                    {
                        Tweet tweet = Tweet.fromJSON(statuses.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() -1 );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClinet", response.toString());
                // iterate through the JSOn array
                // for each energy, deserialize the JSON object

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClinet", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClinet", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClinet", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }


}
