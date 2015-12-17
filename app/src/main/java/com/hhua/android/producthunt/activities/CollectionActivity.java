package com.hhua.android.producthunt.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhua.android.producthunt.ProductHuntApplication;
import com.hhua.android.producthunt.ProductHuntClient;
import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.adapters.TechHuntsArrayAdapter;
import com.hhua.android.producthunt.fragments.CollectionsFragment;
import com.hhua.android.producthunt.models.Collection;
import com.hhua.android.producthunt.models.Post;
import com.hhua.android.producthunt.models.TechHunt;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class CollectionActivity extends AppCompatActivity {
    private ProductHuntClient client;
    private Collection collection;
    private List<TechHunt> techHunts;
    private ListView lvPosts;
    private TechHuntsArrayAdapter techHuntsAdapter;
    private final String LOG_D = "CollectionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.transparent_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int collectionId = intent.getIntExtra(CollectionsFragment.EXTRA_COLLECTION_ID_MESSAGE, -1);

        setTitle("");

        if (collectionId == -1){
            Log.d(LOG_D, "Collection ID (" + collectionId + ") incorrect!");
            return;
        }

        toolbar.bringToFront();

        lvPosts = (ListView) findViewById(R.id.lvPosts);
        lvPosts.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TechHunt techHunt = techHunts.get(position);

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra(Post.POST_ID_MESSAGE, techHunt.getId());

                startActivity(intent);
            }
        });
        techHunts = new ArrayList<TechHunt>();
        techHuntsAdapter = new TechHuntsArrayAdapter(getApplicationContext(), techHunts);
        lvPosts.setAdapter(techHuntsAdapter);

        client = ProductHuntApplication.getRestClient();
        client.getCollection(collectionId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOG_D, response.toString());

                try{
                    collection = Collection.fromJSON(response.getJSONObject("collection"));

                    TextView tvCollectionHeaderName = (TextView) findViewById(R.id.tvCollectionHeaderName);
                    tvCollectionHeaderName.setText(collection.getName());

                    final RelativeLayout collectionHeader = (RelativeLayout) findViewById(R.id.collectionHeader);
                    if(collection.getBackgroundImageUrl() != null){
                        Picasso.with(getApplicationContext()).load(collection.getBackgroundImageUrl()).resize(500, 300).centerCrop().into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                collectionHeader.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                                toolbar.bringToFront();
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Log.d("COLLECTION_BITMAP", "FAILED");
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Log.d("COLLECTION_BITMAP", "FAILED");
                            }
                        });
                    }

                    TextView tvCollectionPageProductCount = (TextView) findViewById(R.id.tvCollectionPageProductCount);
                    tvCollectionPageProductCount.setText(collection.getPostsCount() + " products");

                    techHuntsAdapter.addAll(TechHunt.fromJSONArray(response.getJSONObject("collection").getJSONArray("posts")));
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(LOG_D, errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish(); // Destroys the Activity and goes back the activity which starts it.
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
