package com.hhua.android.producthunt.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.adapters.CommentsArrayAdapter;
import com.hhua.android.producthunt.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {
    private List<Comment> comments;
    private ListView lvComments;
    private CommentsArrayAdapter commentsArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (comments == null){
            comments = new ArrayList<Comment>();
        }

        commentsArrayAdapter = new CommentsArrayAdapter(getContext(), comments);
        lvComments = (ListView) view.findViewById(R.id.lvComments);
        lvComments.setAdapter(commentsArrayAdapter);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
