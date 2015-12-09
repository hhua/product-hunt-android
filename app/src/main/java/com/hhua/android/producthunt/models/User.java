package com.hhua.android.producthunt.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String name;
    private String headline;
    private String username;
    private String twitterName;
    private String websiteUrl;
    private String smallProfileImageUrl;
    private String mediumProfileImageUrl;
    private String largeProfileImageUrl;

    public static User fromJSON(JSONObject jsonObject){
        User user = new User();

        try {
            user.id = jsonObject.getInt("id");
            user.name = jsonObject.getString("name");
            user.headline = jsonObject.getString("headline");
            user.username = jsonObject.getString("username");
            user.twitterName = jsonObject.getString("twitter_username");
            user.websiteUrl = jsonObject.getString("website_url");
            user.smallProfileImageUrl = jsonObject.getJSONObject("image_url").getString("40px");
            user.mediumProfileImageUrl = jsonObject.getJSONObject("image_url").getString("60px");
            user.largeProfileImageUrl = jsonObject.getJSONObject("image_url").getString("96px");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUsername() {
        return username;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getSmallProfileImageUrl() {
        return smallProfileImageUrl;
    }

    public String getMediumProfileImageUrl() {
        return mediumProfileImageUrl;
    }

    public String getLargeProfileImageUrl() {
        return largeProfileImageUrl;
    }
}
