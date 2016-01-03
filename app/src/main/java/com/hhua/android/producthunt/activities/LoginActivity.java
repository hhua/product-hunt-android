package com.hhua.android.producthunt.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.parse.ParseAnalytics;

public class LoginActivity extends OAuthLoginActionBarActivity<ProductHuntClient> {
    private static final String AUTHORIZATION_REDIRECT_URL = "https://github.com/hhua/product-hunt-android";
    private static final String RESPONSE_ACCESS_GRANT = "code";
    private static final String LOG_D = "LoginActivity";
    private static final String LOG_AUTHROIZE = "LOGIN_AUTHORIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            Uri uri = intent.getData();

            if (uri.toString().startsWith(AUTHORIZATION_REDIRECT_URL)){ // coming from authorization url
                String authorizationCode = uri.getQueryParameter(RESPONSE_ACCESS_GRANT);

                if (authorizationCode == null) {
                    Log.i(LOG_AUTHROIZE, "The user doesn't allow authorization.");
                }else {
                    Log.i(LOG_AUTHROIZE, "Authorization code received: " + authorizationCode);


                    // Request access token
                    //getClient().authorize(uri);

                }
            }
        }
    }

    // OAuth authenticated successfully, launch primary authenticated activity
    // i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        Log.d(LOG_D, "Success!");
    }

    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
        Log.d(LOG_D, "Failure!");
    }

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }
}
