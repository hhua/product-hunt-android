package com.hhua.android.producthunt.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.adapters.MediaArrayAdapter;
import com.hhua.android.producthunt.models.Media;

import java.util.ArrayList;
import java.util.List;

public class MediaFragment extends Fragment {

    private List<Media> mediaList;
    private ListView lvMediaList;
    private MediaArrayAdapter mediaArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mediaList == null){
            mediaList = new ArrayList<Media>();
        }

        mediaArrayAdapter = new MediaArrayAdapter(getContext(), mediaList);
        lvMediaList = (ListView) view.findViewById(R.id.lvMediaList);
        lvMediaList.setAdapter(mediaArrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }
}
