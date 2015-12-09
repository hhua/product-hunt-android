package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.models.TechHunt;

import java.util.List;

public class TechHuntsArrayAdapter extends ArrayAdapter<TechHunt> {
    public TechHuntsArrayAdapter(Context context, List<TechHunt> techHunts){
        super(context, android.R.layout.simple_list_item_1, techHunts);
    }

    // viewHolder Pattern
    public View getView(int position, View convertView, ViewGroup parent){
        TechHunt techHunt = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }

        ImageView ivVoteIcon = (ImageView) convertView.findViewById(R.id.ivVoteIcon);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        //TextView tvVoteCount = (TextView) convertView.findViewById(R.id.tvVoteCount);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvTagline = (TextView) convertView.findViewById(R.id.tvTagline);


        tvTitle.setText(techHunt.getName());
        tvTagline.setText(techHunt.getTagline());
        //tvVoteCount.setText(techHunt.getVotesCount());
        //ivVoteIcon.setImageResource(android.R.color.transparent);
        ivProfile.setImageResource(android.R.color.transparent);

        if (techHunt.isVotedForPost()) {
            ivVoteIcon.setImageResource(R.drawable.ic_voted);
        }else {
            ivVoteIcon.setImageResource(R.drawable.ic_voteup);
        }

        // TODO: load hunter profile image

        return convertView;
    }
}
