package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class detailViewActivity extends AppCompatActivity {

    private ImageView ivProfileImage;
    private TextView screenName;
    private TextView userName;
    private TextView dateCreated;
    private TextView tweetText;
    private ImageButton likeButton;
    private ImageButton retweetButton;
    private ImageButton replyButton;

    TwitterClient client;
    TweetAdapter tweetAdapter;
    Tweet detailTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        detailTweet = getIntent().getParcelableExtra("tweet");
        client = TwitterApp.getRestClient();
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        screenName = (TextView) findViewById(R.id.screenName);
        userName = (TextView) findViewById(R.id.userName);
        dateCreated = (TextView) findViewById(R.id.dateCreated);
        tweetText = (TextView) findViewById(R.id.tweetText);
        likeButton = (ImageButton) findViewById(R.id.likeButton);
        retweetButton = (ImageButton) findViewById(R.id.retweetButton);
        replyButton = (ImageButton) findViewById(R.id.replyButton);
        if(detailTweet.favorited)
        {
            likeButton.setBackgroundResource(R.drawable.ic_vector_heart);
        }
        else
        {
            likeButton.setBackgroundResource(R.drawable.ic_vector_heart_stroke);
        }
        if(detailTweet.retweeted)
        {
            retweetButton.setBackgroundResource(R.drawable.ic_vector_retweet);
        }
        else
        {
            retweetButton.setBackgroundResource(R.drawable.ic_vector_retweet_stroke);
        }

        Glide.with(this).
                load(detailTweet.user.profileImageUrl).
                bitmapTransform(new RoundedCornersTransformation(this, 15, 0)).
                into(ivProfileImage);
        screenName.setText(detailTweet.getUser().name);
        userName.setText("@" + detailTweet.getUser().screenName);
        dateCreated.setText(getRelativeTimeAgo(detailTweet.getCreatedAt()));
        tweetText.setText(detailTweet.getBody());
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!detailTweet.favorited)
                {
                    client.favTweet(detailTweet, new JsonHttpResponseHandler(){
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            likeButton.setImageResource(R.drawable.ic_vector_heart);
                            detailTweet.favorited = true;
                        }
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
                else
                {
                    client.unFavTweet(detailTweet, new JsonHttpResponseHandler(){
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            likeButton.setImageResource(R.drawable.ic_vector_heart_stroke);
                            detailTweet.favorited = false;
                        }
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
        });
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tweet replyTweet = detailTweet;
                Intent i = new Intent(detailViewActivity.this, replyActivity.class);
                i.putExtra("tweet", replyTweet);
                detailViewActivity.this.startActivity(i);
            }
        });
        retweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailTweet.retweeted) {
                    client.reTweet(detailTweet, new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            retweetButton.setImageResource(R.drawable.ic_vector_retweet);
                            detailTweet.retweeted = true;
                        }

                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("TwitterClient", responseString);
                            throwable.printStackTrace();
                            Toast.makeText(detailViewActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClinet", errorResponse.toString());
                            throwable.printStackTrace();
                            Toast.makeText(detailViewActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("TwitterClinet", errorResponse.toString());
                            throwable.printStackTrace();
                            Toast.makeText(detailViewActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    client.unReTweet(detailTweet, new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            retweetButton.setImageResource(R.drawable.ic_vector_retweet_stroke);
                            detailTweet.retweeted = false;
                        }

                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("TwitterClient", responseString);
                            throwable.printStackTrace();
                            Toast.makeText(detailViewActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClinet", errorResponse.toString());
                            throwable.printStackTrace();
                            Toast.makeText(detailViewActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("TwitterClinet", errorResponse.toString());
                            throwable.printStackTrace();
                            Toast.makeText(detailViewActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    public String getRelativeTimeAgo(String rawJsonDate){
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
    }