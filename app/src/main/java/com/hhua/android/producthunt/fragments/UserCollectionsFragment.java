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
import com.hhua.android.producthunt.activities.CollectionActivity;
import com.hhua.android.producthunt.adapters.CollectionsArrayAdapter;
import com.hhua.android.producthunt.adapters.EndlessScrollListener;
import com.hhua.android.producthunt.models.Collection;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class UserCollectionsFragment extends Fragment {
    private final String LOG_D = "UserCollectionsFragment";

    private ProductHuntClient client;
    private ListView lvCollections;
    private List<Collection> collections;
    private CollectionsArrayAdapter collectionsArrayAdapter;
    private int userId;
    private int currentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_collections, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = ProductHuntApplication.getRestClient();

        lvCollections = (ListView) view.findViewById(R.id.lvCollections);
        lvCollections.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });

        lvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Collection collection = collectionsArrayAdapter.getItem(position);

                Intent intent = new Intent(getContext(), CollectionActivity.class);
                intent.putExtra(Collection.COLLECTION_ID_MESSAGE, collection.getId());

                startActivity(intent);
            }
        });

        collections = new ArrayList<Collection>();
        collectionsArrayAdapter = new CollectionsArrayAdapter(getContext(), collections);
        lvCollections.setAdapter(collectionsArrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateCollections(currentPage);
    }

    public void customLoadMoreDataFromApi(int offset) {
        populateCollections(currentPage + 1);
    }

    public void populateCollections(final int page){
        client.getCollectionsByUser(userId, page, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Collection> collections = Collection.fromJSONArray(response.getJSONArray("collections"));
                    collectionsArrayAdapter.addAll(collections);
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
