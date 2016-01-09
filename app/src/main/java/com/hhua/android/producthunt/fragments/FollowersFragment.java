package com.hhua.android.producthunt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.activities.UserActivity;
import com.hhua.android.producthunt.adapters.EndlessScrollListener;
import com.hhua.android.producthunt.adapters.FollowersArrayAdapter;
import com.hhua.android.producthunt.models.Follower;
import com.hhua.android.producthunt.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FollowersFragment extends Fragment {
    private final String LOG_D = "FollowersFragment";

    private ProductHuntClient client;
    private ListView lvUsers;
    private List<Follower> followers;
    private FollowersArrayAdapter followersArrayAdapter;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = ProductHuntApplication.getRestClient();

        lvUsers = (ListView) view.findViewById(R.id.lvUsers);
        lvUsers.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });

        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = followers.get(position).getUser();
                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.putExtra(User.USER_ID_MESSAGE, user.getId());

                startActivity(intent);
            }
        });

        followers = new ArrayList<Follower>();
        followersArrayAdapter = new FollowersArrayAdapter(getContext(), followers);
        lvUsers.setAdapter(followersArrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateUsers(-1);
    }

    public void customLoadMoreDataFromApi(int offset) {
        Follower oldestFollower = followersArrayAdapter.getItem(followersArrayAdapter.getCount() - 1);
        populateUsers(oldestFollower.getId());
    }

    public void populateUsers(int olderId){
        client.getFollowers(userId, olderId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    List<Follower> followers = Follower.fromJSONArray(response.getJSONArray("followers"));
                    followersArrayAdapter.addAll(followers);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
