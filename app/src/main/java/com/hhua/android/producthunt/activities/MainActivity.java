package com.hhua.android.producthunt.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.fragments.CollectionsFragment;
import com.hhua.android.producthunt.fragments.NotificationsFragment;
import com.hhua.android.producthunt.fragments.TechPostsFragment;
import com.hhua.android.producthunt.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    private final String LOG_D = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        // Set navigation view header
        loadNavigationViewHeader(navigationView);

        // Set navigation view body
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        // Set up startup fragment
        Class defaultFragmentClass = TechPostsFragment.class;
        Fragment defaultFragment = null;
        try {
            defaultFragment = (Fragment) defaultFragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, defaultFragment).commit();
        setTitle("Products");
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = TechPostsFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = CollectionsFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = NotificationsFragment.class;
                break;
            case R.id.sign_out:
                ProductHuntApplication.getRestClient().clearAccessToken();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return;
            case R.id.follow_me_twitter:
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/hhua_"));
                startActivity(twitterIntent);
                return;
            case R.id.give_me_five_stars:
                Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.hhua.android.producthunt"));
                startActivity(googlePlayIntent);
                return;
            case R.id.provide_feedback:
                String uriText =
                        "mailto:hhua.dev@gmail.com" +
                                "?subject=" + Uri.encode("Feedback on Product Hunt Android Client") +
                                "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                startActivity(Intent.createChooser(sendIntent, "Send email"));
                return;
            default:
                fragmentClass = TechPostsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void loadNavigationViewHeader(final NavigationView navigationView){
        ProductHuntApplication.getRestClient().getSettings(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    final User me = User.fromJSON(response.getJSONObject("user"));

                    ImageView userProfileImage = (ImageView) navigationView.findViewById(R.id.ivDrawerUserProfile);
                    TextView tvDrawerUserName = (TextView) navigationView.findViewById(R.id.tvDrawerUserName);
                    TextView tvDrawerUserHeadline = (TextView) navigationView.findViewById(R.id.tvDrawerUserHeadline);
                    TextView tvDrawerUserTwitterName = (TextView) navigationView.findViewById(R.id.tvDrawerUserTwitterName);

                    tvDrawerUserName.setText(me.getName());
                    tvDrawerUserHeadline.setText(me.getHeadline());
                    tvDrawerUserTwitterName.setText("@" + me.getTwitterName());
                    Picasso.with(getApplicationContext()).load(me.getLargeProfileImageUrl()).fit().into(userProfileImage);


                    userProfileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int userId = me.getId();

                            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                            intent.putExtra(User.USER_ID_MESSAGE, userId);

                            startActivity(intent);
                        }
                    });

                    Picasso.with(getApplicationContext()).load(me.getBackgroundImageUrl()).resize(300, 200).centerCrop().into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            navigationView.getHeaderView(0).setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

}
