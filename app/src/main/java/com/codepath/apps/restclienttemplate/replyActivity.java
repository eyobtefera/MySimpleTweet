package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.id.tweetBox;

public class replyActivity extends AppCompatActivity {
    Tweet replyTweet;
    TwitterClient client;
    String tweet;
    final int RESULT_CODE = 1;
    private TextView mTextView;
    public EditText userTweet;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            mTextView.setText(String.valueOf(140-s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        replyTweet = getIntent().getParcelableExtra("tweet");
        client = TwitterApp.getRestClient();
        userTweet = (EditText) findViewById(tweetBox);
        mTextView = (TextView) findViewById(R.id.tvCounter);
        userTweet.addTextChangedListener(mTextEditorWatcher);
        userTweet.setText("@" + replyTweet.getUserName() + " ");
    }

    public void onSubmit(View v){
        tweet = userTweet.getText().toString();
        client.replyTweet(tweet, replyTweet, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent(replyActivity.this, TimelineActivity.class);
                    data.putExtra("tweet", tweet);
                    setResult(RESULT_CODE, data);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
