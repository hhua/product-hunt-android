package com.hhua.android.producthunt.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.fragments.CommentsFragment;
import com.hhua.android.producthunt.fragments.MediaFragment;
import com.hhua.android.producthunt.models.Media;
import com.hhua.android.producthunt.models.Post;
import com.hhua.android.producthunt.models.TechHunt;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {
    private ProductHuntClient client;
    private TechHunt techHunt;
    private final String LOG_D = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.transparent_toolbar);
        setSupportActionBar(toolbar);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int postId = intent.getIntExtra(Post.POST_ID_MESSAGE, -1);

        setTitle("");

        if (postId == -1){
            // Post ID incorrect
            return;
        }

        // Get post details
        client = ProductHuntApplication.getRestClient();
        client.getPost(postId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // process response
                try {
                    techHunt = TechHunt.fromJSON(response.getJSONObject("post"));

                    // Get the view pager
                    ViewPager vpPager = (ViewPager) findViewById(R.id.detailPageViewPager);
                    // Set the view pager adapter to the pager
                    vpPager.setAdapter(new DetailsPagerAdapter(getSupportFragmentManager()));
                    // Find the pager sliding tabs
                    PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.detailPageTabs);
                    // Attach pager tabs to the viewpager
                    tabStrip.setViewPager(vpPager);

                    // show techHunt in views
                    TextView tvDetailsPageHeaderTitle = (TextView) findViewById(R.id.tvDetailsPageHeaderTitle);
                    TextView tvDetailsPageHeaderTagline = (TextView) findViewById(R.id.tvDetailsPageHeaderTagline);
                    Button btnDetailsPageVote = (Button) findViewById(R.id.btnDetailsPageVote);
                    //Button btnDetailsPageGetIt = (Button) findViewById(R.id.btnDetailsPageGetIt);

                    tvDetailsPageHeaderTitle.setText(techHunt.getName());
                    tvDetailsPageHeaderTagline.setText(techHunt.getTagline());

                    btnDetailsPageVote.setText(techHunt.getVotesCount());

                    if (techHunt.isVotedForPost()){
                        btnDetailsPageVote.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_voted, 0, 0, 0);
                        btnDetailsPageVote.setTextColor(Color.parseColor("#da552f"));
                    }else{
                        btnDetailsPageVote.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_voteup, 0, 0, 0);
                        btnDetailsPageVote.setTextColor(Color.GRAY);
                    }

                    List<Media> mediaList = techHunt.getMediaList();
                    toolbar.bringToFront();
                    if (mediaList.size() > 0 && techHunt.getHeaderMediaId() > 0){
                        String imageUrl = null;
                        for(int i = 0; i < mediaList.size(); i++){
                            if (techHunt.getHeaderMediaId() == mediaList.get(i).getId()){
                                imageUrl = mediaList.get(i).getImageUrl();
                            }
                        }
                        final RelativeLayout detailsHeader = (RelativeLayout) findViewById(R.id.detailsHeader);

                        if(imageUrl != null){
                            Picasso.with(getApplicationContext()).load(imageUrl).resize(500, 300).centerCrop().into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    detailsHeader.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                                    toolbar.bringToFront();
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                }
                            });
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
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

    public void openPostLink(View view){
        if(techHunt != null){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(techHunt.getRedirectUrl()));
            startActivity(browserIntent);
        }
    }

    // Return the order of the fragment in the view pager
    public class DetailsPagerAdapter extends FragmentPagerAdapter{
        private String tabTitles[] = {"Comments", "Media"};

        public DetailsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                CommentsFragment commentsFragment = new CommentsFragment();
                commentsFragment.setComments(techHunt.getComments());
                return commentsFragment;
            }else if (position == 1){
                MediaFragment mediaFragment = new MediaFragment();
                mediaFragment.setMediaList(techHunt.getMediaList());
                return mediaFragment;
            }else
                return null;
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