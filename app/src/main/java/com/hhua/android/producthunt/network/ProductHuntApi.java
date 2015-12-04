package com.hhua.android.producthunt.network;

import com.hhua.android.producthunt.services.ProductHunt20ServiceImpl;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

public class ProductHuntApi extends DefaultApi20 {
    // Product Hunt API is using OAuth2 (Three legged)
    private static final String AUTHORIZE_URL = "https://api.producthunt.com/v1/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=public+private";
    private static final String ACCESS_TOKEN_URL = "https://api.producthunt.com/v1/oauth/token";

    private static final String LOG_D = "PRODUCT_HUNT_API";

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public String getAccessTokenEndpoint() {
        final String accessTokenEndpoint = ACCESS_TOKEN_URL;

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

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    @Override
    public OAuthService createService(final OAuthConfig config) {
        return new ProductHunt20ServiceImpl(this, config);
    }
}
