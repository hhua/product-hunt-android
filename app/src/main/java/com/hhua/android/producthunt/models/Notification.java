package com.hhua.android.producthunt.models;

import com.hhua.android.producthunt.helpers.TimeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private int id;
    private boolean isSeen;
    private String sentence;
    private String type;
    private String body;
    private int referenceId;
    private User fromUser;
    private String createdAt;
    private User toUser;

    public static Notification fromJSON(JSONObject jsonObject){
        Notification notification = new Notification();

        try{
            notification.id = jsonObject.getInt("id");
            notification.isSeen = jsonObject.getBoolean("seen");
            notification.sentence = jsonObject.getString("sentence");
            notification.type = jsonObject.getString("type");
            notification.body = jsonObject.getString("body");
            notification.referenceId = jsonObject.getJSONObject("reference").getInt("id");
            notification.fromUser = User.fromJSON(jsonObject.getJSONObject("from_user"));
            //notification.toUser = User.fromJSON(jsonObject.getJSONObject("to_user"));
            notification.createdAt = jsonObject.getString("created_at");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return notification;
    }

    public static List<Notification> fromJSONArray(JSONArray jsonArray){
        List<Notification> notifications = new ArrayList<Notification>();

        for(int i = 0; i < jsonArray.length(); i++){
            try{
                JSONObject notificationJson = jsonArray.getJSONObject(i);
                Notification notification = Notification.fromJSON(notificationJson);

                if(notification != null){
                    notifications.add(notification);
                }
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return notifications;
    }

    public int getId() {
        return id;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public String getSentence() {
        return sentence;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public String getCreatedAt() {
        return TimeHelper.getRelativeTimeAgo(createdAt);
    }

    public User getToUser() {
        return toUser;
    }
}
