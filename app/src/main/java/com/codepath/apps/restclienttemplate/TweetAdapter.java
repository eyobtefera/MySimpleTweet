package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by eyobtefera on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
    TwitterClient client;
    //for each row, inflate the layout and cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        client = TwitterApp.getRestClient();
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get the data according to position
        Tweet tweet = mTweets.get(position);
        String time = tweet.createdAt;
        String displayTime = getRelativeTimeAgo(time);
        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name + " " + "@" + tweet.user.screenName);
        holder.tvBody.setText(tweet.body);
        holder.tvCreation.setText("•" + displayTime);

        Glide.with(context).
                load(tweet.user.profileImageUrl).
                bitmapTransform(new RoundedCornersTransformation(context, 15, 0)).
                into(holder.ivProfileImage);
        if(tweet.favorited)
        {
            holder.likeButton.setBackgroundResource(R.drawable.ic_vector_heart);
        }
        else
        {
            holder.likeButton.setBackgroundResource(R.drawable.ic_vector_heart_stroke);
        }
        if(tweet.retweeted)
        {
            holder.retweetButton.setBackgroundResource(R.drawable.ic_vector_retweet);
        }
        else
        {
            holder.retweetButton.setBackgroundResource(R.drawable.ic_vector_retweet_stroke);
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvCreation;
        public ImageButton ivReply;
        public ImageButton likeButton;
        public ImageButton retweetButton;
        public RelativeLayout layout;
        public ViewHolder(View itemView){
            super(itemView);

            // perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername =  (TextView) itemView.findViewById(R.id.tvName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreation = (TextView) itemView.findViewById(R.id.tvCreation);
            ivReply = (ImageButton) itemView.findViewById(R.id.replyButton);
            layout = (RelativeLayout) itemView.findViewById(R.id.detailView);
            likeButton = (ImageButton) itemView.findViewById(R.id.likeButton);
            retweetButton = (ImageButton) itemView.findViewById(R.id.retweetButton);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    final Tweet replyTweet = mTweets.get(pos);
                    if(!replyTweet.favorited)
                        {
                            client.favTweet(replyTweet, new JsonHttpResponseHandler(){
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    likeButton.setImageResource(R.drawable.ic_vector_heart);
                                    replyTweet.favorited = true;
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
                            client.unFavTweet(replyTweet, new JsonHttpResponseHandler(){
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    likeButton.setImageResource(R.drawable.ic_vector_heart_stroke);
                                    replyTweet.favorited = false;
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
            retweetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    final Tweet replyTweet = mTweets.get(pos);
                    if (!replyTweet.retweeted) {
                        client.reTweet(replyTweet, new JsonHttpResponseHandler() {
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                retweetButton.setImageResource(R.drawable.ic_vector_retweet);
                                replyTweet.retweeted = true;
                            }

                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("TwitterClient", responseString);
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
                    else{
                        client.unReTweet(replyTweet, new JsonHttpResponseHandler() {
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                retweetButton.setImageResource(R.drawable.ic_vector_retweet_stroke);
                                replyTweet.retweeted = false;
                            }

                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("TwitterClient", responseString);
                                throwable.printStackTrace();}

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
            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Tweet replyTweet = mTweets.get(pos);
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("tweet", replyTweet);
                    i.putExtra("userProfile", false);
                    context.startActivity(i);
                }
            });
            ivReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Tweet replyTweet = mTweets.get(pos);
                    Intent i = new Intent(context, replyActivity.class);
                    i.putExtra("tweet", replyTweet);
                    context.startActivity(i);
                }
            });
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Tweet detailTweet = mTweets.get(pos);
                    Intent i = new Intent(context, detailViewActivity.class);
                    i.putExtra("tweet", detailTweet);
                    context.startActivity(i);
                }
            });
            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Tweet detailTweet = mTweets.get(pos);
                    Intent i = new Intent(context, detailViewActivity.class);
                    i.putExtra("tweet", detailTweet);
                    context.startActivity(i);
                }
            });
        }
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
    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }
    public void onButtonClick(View view) {

    }
}
