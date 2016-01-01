package com.hhua.android.producthunt.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static final String USER_ID_MESSAGE = "com.hhua.android.producthunt.user.USER_ID";

    private int id;
    private String name;
    private String headline;
    private String username;
    private String twitterName;
    private String websiteUrl;
    private String smallProfileImageUrl;
    private String mediumProfileImageUrl;
    private String largeProfileImageUrl;
    private String backgroundImageUrl;
    private int votesCount;
    private int postsCount;
    private int makerCount;
    private int collectionsCount;
    private int followersCount;
    private int followingCount;

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
            user.backgroundImageUrl = jsonObject.getJSONObject("image_url").getString("original");
            user.votesCount = jsonObject.optInt("votes_count");
            user.postsCount = jsonObject.optInt("posts_count");
            user.makerCount = jsonObject.optInt("maker_of_count");
            user.collectionsCount = jsonObject.optInt("collections_count");
            user.followersCount = jsonObject.optInt("followers_count");
            user.followingCount = jsonObject.optInt("followings_count");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> fromJSONArray(JSONArray jsonArray){
        List<User> users = new ArrayList<User>();

        for (int i = 0; i < jsonArray.length(); i++){
            try {
                User user = User.fromJSON(jsonArray.getJSONObject(i));
                users.add(user);
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return users;
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

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public int getMakerCount() {
        return makerCount;
    }

    public int getCollectionsCount() {
        return collectionsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }
}
