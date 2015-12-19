package com.hhua.android.producthunt.models;

import com.hhua.android.producthunt.helpers.TimeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahua on 12/18/15.
 */
public class Comment {
    private int id;
    private String body;
    private String createdAt;
    private int voteCount;
    private User user;
    private List<Comment> childComments;

    public static Comment fromJSON(JSONObject jsonObject){
        Comment comment = new Comment();

        try {
            comment.id = jsonObject.getInt("id");
            comment.body = jsonObject.getString("body");
            comment.createdAt = jsonObject.getString("created_at");
            comment.voteCount = jsonObject.getInt("votes");
            comment.user = User.fromJSON(jsonObject.getJSONObject("user"));
            comment.childComments = Comment.fromJSONArray(jsonObject.getJSONArray("child_comments"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        return comment;
    }

    public static List<Comment> fromJSONArray(JSONArray jsonArray){
        List<Comment> comments = new ArrayList<Comment>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject commentJson = jsonArray.getJSONObject(i);
                Comment comment = Comment.fromJSON(commentJson);

                if (comment != null){
                    comments.add(comment);
                }
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }

        return comments;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return TimeHelper.getRelativeTimeAgo(createdAt);
    }

    public int getVoteCount() {
        return voteCount;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getChildComments() {
        return childComments;
    }
}
