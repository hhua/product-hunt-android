package com.hhua.android.producthunt.services;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.oauth.OAuth20ServiceImpl;

public class ProductHunt20ServiceImpl extends OAuth20ServiceImpl {

    public ProductHunt20ServiceImpl(final DefaultApi20 api, final OAuthConfig config) {
        super(api, config);
    }

//    @Override
//    protected <T extends AbstractRequest> T createAccessTokenRequest(final Verifier verifier, T request) {
//        super.createAccessTokenRequest(verifier, request);
//        if (!getConfig().hasGrantType()) {
//            request.addParameter(OAuthConstants.GRANT_TYPE, "authorization_code");
//        }
//        return (T) request;
//    }
}
