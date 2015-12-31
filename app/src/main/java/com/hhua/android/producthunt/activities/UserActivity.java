package com.hhua.android.producthunt.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.fragments.PostsFragment;
import com.hhua.android.producthunt.models.TechHunt;
import com.hhua.android.producthunt.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class UserActivity extends AppCompatActivity {
    private final String LOG_D = "UserActivity";

    private ProductHuntClient client;
    private User user;
    private List<TechHunt> votedPosts;
    private List<TechHunt> submittedPosts;
    private List<TechHunt> makerPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.transparent_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int userId = intent.getIntExtra(User.USER_ID_MESSAGE, -1);

        setTitle("");

        if (userId == -1){
            Log.d(LOG_D, "User ID (" + userId + ") incorrect!");
            return;
        }

        client = ProductHuntApplication.getRestClient();
        client.getUser(userId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOG_D, response.toString());

                try {
                    user = User.fromJSON(response.getJSONObject("user"));

                    TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
                    TextView tvUserDescription = (TextView) findViewById(R.id.tvUserDescription);
                    TextView tvTwitterUserName = (TextView) findViewById(R.id.tvTwitterUserName);
                    ImageView ivUserProfile = (ImageView) findViewById(R.id.ivUserProfile);
                    ivUserProfile.setImageResource(0);

                    tvUserName.setText(user.getName());
                    tvUserDescription.setText(user.getHeadline());
                    tvTwitterUserName.setText("@" + user.getTwitterName());

                    Picasso.with(getApplicationContext()).load(user.getLargeProfileImageUrl()).fit().into(ivUserProfile);

                    final RelativeLayout userPageHeader = (RelativeLayout) findViewById(R.id.userPageHeader);
                    toolbar.bringToFront();
                    Picasso.with(getApplicationContext()).load(user.getBackgroundImageUrl()).resize(500, 300).centerCrop().into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            userPageHeader.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                            toolbar.bringToFront();
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Log.d("USER_BITMAP", "FAILED");
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Log.d("USER_BITMAP", "Prepare Load");
                        }
                    });

                    // Load page slider data
                    votedPosts = new ArrayList<TechHunt>();
                    JSONArray votes = response.getJSONObject("user").getJSONArray("votes");
                    for(int i = 0; i < votes.length(); i++){
                        votedPosts.add(TechHunt.fromJSON(votes.getJSONObject(i).getJSONObject("post")));
                    }

                    submittedPosts = new ArrayList<TechHunt>();
                    submittedPosts.addAll(TechHunt.fromJSONArray(response.getJSONObject("user").getJSONArray("posts")));

                    makerPosts = new ArrayList<TechHunt>();
                    makerPosts.addAll(TechHunt.fromJSONArray(response.getJSONObject("user").getJSONArray("maker_of")));

                    // Get the view pager
                    ViewPager vpPager = (ViewPager) findViewById(R.id.userPageViewPager);
                    // Set the view pager adapter to the pager
                    vpPager.setAdapter(new UserPagerAdapter(getSupportFragmentManager()));
                    // Find the pager sliding tabs
                    PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.userPageTabs);
                    // Attach pager tabs to the viewpager
                    tabStrip.setViewPager(vpPager);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(LOG_D, errorResponse.toString());
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

    // Return the order of the fragment in the view pager
    public class UserPagerAdapter extends FragmentPagerAdapter {
        //private String tabTitles[] = {"Upvoted", "Submitted", "Collections", "Made", "Following", "Followers"};
        private String tabTitles[] = {"Upvoted", "Submitted", "Made"};


        public UserPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    PostsFragment votedPostsFragment = new PostsFragment();
                    votedPostsFragment.setPosts(votedPosts);
                    return votedPostsFragment;
                case 1:
                    PostsFragment submittedPostsFragment = new PostsFragment();
                    submittedPostsFragment.setPosts(submittedPosts);
                    return submittedPostsFragment;
                case 2:
                    PostsFragment makerPostsFragment = new PostsFragment();
                    makerPostsFragment.setPosts(makerPosts);
                    return makerPostsFragment;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
