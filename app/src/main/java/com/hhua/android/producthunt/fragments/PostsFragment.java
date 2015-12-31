package com.hhua.android.producthunt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.activities.DetailsActivity;
import com.hhua.android.producthunt.adapters.TechHuntsArrayAdapter;
import com.hhua.android.producthunt.models.Post;
import com.hhua.android.producthunt.models.TechHunt;

import java.util.List;

public class PostsFragment extends Fragment {
    private TechHuntsArrayAdapter techHuntsAdapter;
    private ListView lvTechHunts;
    private List<TechHunt> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (posts == null){
            return;
        }

        lvTechHunts = (ListView) view.findViewById(R.id.lvTechHunts);
        lvTechHunts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TechHunt techHunt = posts.get(position);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(Post.POST_ID_MESSAGE, techHunt.getId());

                startActivity(intent);
            }
        });

        techHuntsAdapter = new TechHuntsArrayAdapter(getContext(), posts);
        lvTechHunts.setAdapter(techHuntsAdapter);
    }

    public void setPosts(List<TechHunt> posts) {
        this.posts = posts;
    }
}
