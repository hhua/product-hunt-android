package com.hhua.android.producthunt.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahua on 12/10/15.
 */
public class Collection {
    private int id;
    private String name;
    private String title;
    private String color;
    private String backgroundImageUrl;
    private int postsCount;

    public static Collection fromJSON(JSONObject json){
        Collection collection = new Collection();

        try{
            collection.id = json.getInt("id");
            collection.name = json.getString("name");
            collection.title = json.getString("title");
            collection.color = json.getString("color");
            collection.backgroundImageUrl = json.getString("background_image_url");
            collection.postsCount = json.getInt("posts_count");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return collection;
    }

    public static List<Collection> fromJSONArray(JSONArray jsonArray){
        List<Collection> collections = new ArrayList<Collection>();

        for(int i = 0; i < jsonArray.length(); i++){
            try{
                JSONObject collectionJson = jsonArray.getJSONObject(i);
                Collection collection = Collection.fromJSON(collectionJson);

                if (collection != null){
                    collections.add(collection);
                }
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return collections;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public String getPostsCount() {
        return "" + postsCount;
    }
}
