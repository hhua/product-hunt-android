package com.hhua.android.producthunt;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.hhua.android.producthunt.network.ProductHuntApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;

/**
 * Created by ahua on 11/29/15.
 */
public class ProductHuntClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = ProductHuntApi.class;
    public static final String REST_URL = "https://api.producthunt.com/v1";
    public static final String REST_CONSUMER_KEY = "71b0a27e59e12bcf97f3ca616de14b62120949dea2c8e31562a6f9d6c86bc560";
    public static final String REST_CONSUMER_SECRET = "377edcb30237d30be72fd4bc8f678475e335386cf01ad4efd01fafe8f74931ce";

    public static final String REST_CALLBACK_URL = "https://hhua.github.io";

    public ProductHuntClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // Get Tech Hunts
    // GET /v1/posts?days_ago=1
    public void getTechHunts(int daysAgo, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("posts");

        RequestParams params = new RequestParams();
        if (daysAgo > 0){
            params.put("days_ago", daysAgo);
        }

        // Execute the request
        // No idea why I need to add header by myself
        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }

    // Get Post
    // GET /v1/posts/:id
    public void getPost(int postId, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("posts/" + postId);

        RequestParams params = new RequestParams();
        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }

    // Get Featured Collections
    // GET /v1/collections
    public void getFeaturedCollections(int olderId, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("collections");

        RequestParams params = new RequestParams();

        params.put("sort_by", "featured_at");
        params.put("order", "desc");
        params.put("per_page", 20);
        params.put("search[featured]", true);

        if (olderId > -1) {
            params.put("older", olderId);
        }

        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }

    // Get Selected Collections
    // GET /v1/collections/:id
    public void getCollection(int collectionId, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("collections/" + collectionId);

        RequestParams params = new RequestParams();

        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }

    // Get Current User Info
    // GET /v1/me
    public void getSettings(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("me");

        RequestParams params = new RequestParams();

        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }

    // Get All notifications
    // GET /v1/notifications
    public void getAllNotifications(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("notifications");

        RequestParams params = new RequestParams();
        params.put("per_page", 50);
        params.put("search[type]", "all");

        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }

    // Get a User
    // GET /v1/users/:id / /v1/user/:username
    public void getUser(int userId, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("users/" + userId);

        RequestParams params = new RequestParams();

        getClient().addHeader("Authorization", "Bearer " + getClient().getAccessToken().getToken());
        getClient().get(apiUrl, params, handler);
    }
}
