package com.hhua.android.producthunt.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TechHunt extends Post {
    private String name;
    private String tagline;
    private int votesCount;
    private boolean votedForPost;
    private String day;
    private boolean featured;

    public static TechHunt fromJSON(JSONObject jsonObject){
        TechHunt techHunt = new TechHunt();

        try{
            techHunt.name = jsonObject.getString("name");
            techHunt.tagline = jsonObject.getString("tagline");
            techHunt.votesCount = jsonObject.getInt("votes_count");
            techHunt.votedForPost = jsonObject.getJSONObject("current_user").optBoolean("voted_for_post");
            techHunt.day = jsonObject.getString("day");
            techHunt.featured = jsonObject.getBoolean("featured");
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
}
