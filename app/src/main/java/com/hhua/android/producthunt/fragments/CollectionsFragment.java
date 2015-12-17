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
import com.hhua.android.producthunt.activities.CollectionActivity;
import com.hhua.android.producthunt.adapters.CollectionsArrayAdapter;
import com.hhua.android.producthunt.adapters.CollectionsEndlessScrollListener;
import com.hhua.android.producthunt.models.Collection;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CollectionsFragment extends Fragment {
    private ProductHuntClient client;
    private SwipeRefreshLayout swipeContainer;
    private ListView lvCollections;
    private List<Collection> collections;
    private CollectionsArrayAdapter collectionsAdapter;

    public final static String EXTRA_COLLECTION_ID_MESSAGE = "com.hhua.android.producthunt.collectionsfragment.COLLECTION_ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = ProductHuntApplication.getRestClient();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.collectionsSwipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshCollections();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        lvCollections = (ListView) view.findViewById(R.id.lvCollections);
        lvCollections.setOnScrollListener(new CollectionsEndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });

        lvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Collection collection = collections.get(position);

                Intent intent = new Intent(getContext(), CollectionActivity.class);
                intent.putExtra(EXTRA_COLLECTION_ID_MESSAGE, collection.getId());

                startActivity(intent);
            }
        });

        collections = new ArrayList<>();
        collectionsAdapter = new CollectionsArrayAdapter(getContext(), collections);
        lvCollections.setAdapter(collectionsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateCollections(-1);
    }

    private void refreshCollections(){
        client.getFeaturedCollections(-1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                try {
                    List<Collection> collections = Collection.fromJSONArray(response.getJSONArray("collections"));
                    collectionsAdapter.clear();
                    collectionsAdapter.addAll(collections);

                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });


    }

    public void customLoadMoreDataFromApi(int offset) {
        Collection oldestCollection = collectionsAdapter.getItem(collectionsAdapter.getCount() - 1);
        populateCollections(oldestCollection.getId());
    }

    public void populateCollections(int olderId){
        client.getFeaturedCollections(olderId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                try {
                    List<Collection> collections = Collection.fromJSONArray(response.getJSONArray("collections"));
                    collectionsAdapter.addAll(collections);
                } catch (JSONException e) {
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
