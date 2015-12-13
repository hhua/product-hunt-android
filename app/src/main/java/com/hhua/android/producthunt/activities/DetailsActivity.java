package com.hhua.android.producthunt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.fragments.TechPostsFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {
    private ProductHuntClient client;
    private final String LOG_D = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int postId = intent.getIntExtra(TechPostsFragment.EXTRA_POST_ID_MESSAGE, -1);

        setTitle("Details");

        if (postId == -1){
            Log.d(LOG_D, "Post ID incorrect!");
            return;
        }

        // Get post details
        client = ProductHuntApplication.getRestClient();
        client.getPost(postId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish(); // Destroys the Activity and goes back the activity which starts it.
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}