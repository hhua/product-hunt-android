package com.hhua.android.producthunt.network;

import android.util.Log;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.utils.OAuthEncoder;

public class ProductHuntApi extends DefaultApi20 {
    // Product Hunt API is using OAuth2 (Three legged)
    private static final String AUTHORIZE_URL = "https://api.producthunt.com/v1/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=public+private";
    private static final String ACCESS_TOKEN_RESOURCE = "api.producthunt.com/v1/oauth/token";

    private static final String LOG_D = "PRODUCT_HUNT_API";

    @Override
    public String getAccessTokenEndpoint() {
        final String accessTokenEndpoint = "https://" + ACCESS_TOKEN_RESOURCE;
        Log.d(LOG_D, accessTokenEndpoint);

        return accessTokenEndpoint;
    }

    @Override
    public String getAuthorizationUrl(final OAuthConfig config) {
//        Preconditions.checkValidUrl(config.getCallback(),
//                "Must provide a valid url as callback. Product Hunt does not support OOB");
        final StringBuilder sb = new StringBuilder(String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(
                config.getCallback())));
        if (config.hasScope()) {
            sb.append('&').append(OAuthConstants.SCOPE).append('=').append(OAuthEncoder.encode(config.getScope()));
        }

//        final String state = config.getState();
//        if (state != null) {
//            sb.append('&').append(OAuthConstants.STATE).append('=').append(OAuthEncoder.encode(state));
//        }
        return sb.toString();
    }
}
