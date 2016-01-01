package com.hhua.android.producthunt.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Follower {
    private int id;
    private User user;

    public static Follower fromJSON(JSONObject jsonObject){
        Follower follower = new Follower();

        try{
            follower.id = jsonObject.getInt("id");
            follower.user = User.fromJSON(jsonObject.getJSONObject("user"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        return follower;
    }

    public static List<Follower> fromJSONArray(JSONArray jsonArray){
        List<Follower> followers = new ArrayList<Follower>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                Follower follower = Follower.fromJSON(jsonArray.getJSONObject(i));
                followers.add(follower);
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return followers;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
