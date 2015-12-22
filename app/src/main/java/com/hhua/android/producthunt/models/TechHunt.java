package com.hhua.android.producthunt.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TechHunt extends Post {
    private int id;
    private String name;
    private String tagline;
    private int votesCount;
    private boolean votedForPost;
    private String day;
    private boolean featured;
    private User hunter;
    private String redirectUrl;
    private List<Media> mediaList;
    private List<Comment> comments;
    private int headerMediaId;

    public static TechHunt fromJSON(JSONObject jsonObject){
        TechHunt techHunt = new TechHunt();

        try{
            techHunt.id = jsonObject.getInt("id");
            techHunt.name = jsonObject.getString("name");
            techHunt.tagline = jsonObject.optString("tagline");
            techHunt.votesCount = jsonObject.optInt("votes_count");
            if (!jsonObject.isNull("current_user")){
                techHunt.votedForPost = jsonObject.getJSONObject("current_user").optBoolean("voted_for_post");
            }

            techHunt.day = jsonObject.optString("day");
            techHunt.featured = jsonObject.optBoolean("featured");
            if (!jsonObject.isNull("user")){
                techHunt.hunter = User.fromJSON(jsonObject.getJSONObject("user"));
            }

            techHunt.redirectUrl = jsonObject.optString("redirect_url");
            JSONArray mediaJsonArray = jsonObject.optJSONArray("media");

            if (mediaJsonArray != null){
                techHunt.mediaList = Media.fromJSONArray(mediaJsonArray);
            }
            techHunt.headerMediaId = jsonObject.optInt("header_media_id");

            if (!jsonObject.isNull("comments")){
                techHunt.comments = Comment.fromJSONArray(jsonObject.getJSONArray("comments"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return techHunt;
    }

    public static List<TechHunt> fromJSONArray(JSONArray jsonArray){
        List<TechHunt> techHunts = new ArrayList<TechHunt>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject techHuntJson = jsonArray.getJSONObject(i);
                TechHunt techHunt = TechHunt.fromJSON(techHuntJson);

                if (techHunt != null){
                    techHunts.add(techHunt);
                }
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return techHunts;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public int getHeaderMediaId() {
        return headerMediaId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getVotesCount() {
        return "" + votesCount;
    }

    public boolean isVotedForPost() {
        return votedForPost;
    }

    public String getDay() {
        return day;
    }

    public boolean isFeatured() {
        return featured;
    }

    public User getHunter() {
        return hunter;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
