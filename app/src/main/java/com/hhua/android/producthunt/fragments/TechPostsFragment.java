package com.hhua.android.producthunt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.activities.DetailsActivity;
import com.hhua.android.producthunt.adapters.EndlessScrollListener;
import com.hhua.android.producthunt.adapters.TechHuntsArrayAdapter;
import com.hhua.android.producthunt.models.Post;
import com.hhua.android.producthunt.models.TechHunt;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TechPostsFragment extends Fragment {
    private ProductHuntClient client;
    private SwipeRefreshLayout swipeContainer;
    private TechHuntsArrayAdapter techHuntsAdapter;
    private ListView lvTechHunts;
    private List<TechHunt> techHunts;

    private int daysBefore;

    private final int REQUEST_CODE = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tech_posts, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = ProductHuntApplication.getRestClient();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.techPostsSwipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshTechPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvTechHunts = (ListView) view.findViewById(R.id.lvTechHunts);
        lvTechHunts.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });

        lvTechHunts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TechHunt techHunt = techHunts.get(position);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(Post.POST_ID_MESSAGE, techHunt.getId());

                startActivity(intent);
            }
        });

        techHunts = new ArrayList<>();
        techHuntsAdapter = new TechHuntsArrayAdapter(getContext(), techHunts);
        lvTechHunts.setAdapter(techHuntsAdapter);
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        daysBefore = 0;
        populateTechPosts(0);
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        daysBefore++;
        populateTechPosts(daysBefore);
    }

    private void populateTechPosts(final int daysAgo){
        client.getTechHunts(daysAgo, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                try {
                    List<TechHunt> techHunts = TechHunt.fromJSONArray(response.getJSONArray("posts"));
                    techHuntsAdapter.addAll(techHunts);
                }catch (JSONException e){
                    e.printStackTrace();
                    daysBefore--;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                daysBefore--;
            }

        });
    }

    private void refreshTechPosts() {
        client.getTechHunts(0, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                try {
                    List<TechHunt> techHunts = TechHunt.fromJSONArray(response.getJSONArray("posts"));
                    techHuntsAdapter.clear();
                    techHuntsAdapter.addAll(techHunts);

                    daysBefore = 0;
                    swipeContainer.setRefreshing(false);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
