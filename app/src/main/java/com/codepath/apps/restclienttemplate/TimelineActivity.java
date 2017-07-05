package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;

public class TimelineActivity extends AppCompatActivity {

    final int TWEET_POST_REQUEST = 1;
    final int TWEET_POST_RESULT = 1;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
       /* swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        //populateTimeline();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);//placeholder make sure to change this
        return true;
    }

    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i = new Intent (this, ProfileActivity.class);
        i.putExtra("userProfile", true);
        startActivity(i);
    }
    public void onComposeAction(MenuItem mi){
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, TWEET_POST_REQUEST);
    }

    //@Override
   /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TWEET_POST_REQUEST == requestCode && resultCode == TWEET_POST_RESULT  ){
        /*Tweet tweet = data.getParcelableExtra("tweet");
        tweets.add(0, tweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);*/
        /*homeTimelineFragment timeLine = new HomeTimelineFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(timeLine)
                    .attach(timeLine)
                    .commit();
        }
    }*/

    /*public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClinet", response.toString());
                // iterate through the JSOn array
                // for each energy, deserialize the JSON object
                //tweetAdapter.clear();

                for (int i = 0; i <response.length(); i++){
                    // covert each object to a tweet model
                    // add that tweet model to our data source
                    // notify the adapter that we've added an item
                    try {

                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        //tweets.add(tweet);
                       // tweetAdapter.notifyItemInserted(tweets.size() -1 );
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                swipeContainer.setRefreshing(false);
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
    }*/

}
