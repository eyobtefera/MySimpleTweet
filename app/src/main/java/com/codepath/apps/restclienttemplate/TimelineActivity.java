package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import static com.codepath.apps.restclienttemplate.R.menu.menu_timeline;

public class TimelineActivity extends AppCompatActivity {

    final int TWEET_POST_REQUEST = 1;
    final int TWEET_POST_RESULT = 1;
    final int TWEET_SEARCH_REQUEST = 2;
    Context context;
    private SwipeRefreshLayout swipeContainer;
    ViewPager vpPager;
    TweetsPagerAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        pageAdapter = new TweetsPagerAdapter(getSupportFragmentManager(), this);
        context = this;
        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(pageAdapter);
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_timeline, menu);//placeholder make sure to change this
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("search_tweet", query);
                context.startActivity(i);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

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
    public void onQueryTextSubmit(MenuItem mi){
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i, TWEET_SEARCH_REQUEST);
    }

    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TWEET_POST_REQUEST == requestCode && resultCode == TWEET_POST_RESULT  ){
        Tweet tweet = data.getParcelableExtra("tweet");
            Object o = vpPager.getCurrentItem();
            if (o instanceof HomeTimelineFragment) {
                ((HomeTimelineFragment) o).addTweet(tweet);
            }
            ((HomeTimelineFragment) pageAdapter.getItem(vpPager.getCurrentItem())).addTweet(tweet);
        }
    }
}
