package com.hhua.android.producthunt.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Media{
    private int id;
    private String type;
    private String imageUrl;

    public static Media fromJSON(JSONObject jsonObject){
        Media media = new Media();

        try{
            media.type = jsonObject.getString("media_type");

            if(!media.type.equals("image")){
                return null;
            }

            media.id = jsonObject.getInt("id");
            media.imageUrl = jsonObject.getString("image_url");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return media;
    }

    public static List<Media> fromJSONArray(JSONArray jsonArray){
        List<Media> mediaList = new ArrayList<Media>();

        for(int i = 0; i < jsonArray.length(); i++){
            try{
                JSONObject mediaJson = jsonArray.getJSONObject(i);
                Media media = Media.fromJSON(mediaJson);

                if (media != null){
                    mediaList.add(media);
                }
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return mediaList;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
