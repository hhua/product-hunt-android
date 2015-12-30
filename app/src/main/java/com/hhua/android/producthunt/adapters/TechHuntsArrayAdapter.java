package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.activities.UserActivity;
import com.hhua.android.producthunt.models.TechHunt;
import com.hhua.android.producthunt.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TechHuntsArrayAdapter extends ArrayAdapter<TechHunt> {
    public TechHuntsArrayAdapter(Context context, List<TechHunt> techHunts){
        super(context, android.R.layout.simple_list_item_1, techHunts);
    }

    // viewHolder Pattern
    public View getView(int position, View convertView, ViewGroup parent){
        final TechHunt techHunt = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }

        ImageView ivVoteIcon = (ImageView) convertView.findViewById(R.id.ivVoteIcon);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvVoteCount = (TextView) convertView.findViewById(R.id.tvVoteCount);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvTagline = (TextView) convertView.findViewById(R.id.tvTagline);

        tvTitle.setText(techHunt.getName());
        tvTagline.setText(techHunt.getTagline());
        tvVoteCount.setText(techHunt.getVotesCount());
        //ivVoteIcon.setImageResource(android.R.color.transparent);
        //ivProfile.setImageResource(android.R.color.transparent);
        ivProfile.setImageResource(0);

        if (techHunt.isVotedForPost()) {
            ivVoteIcon.setImageResource(R.drawable.ic_voted);
            tvVoteCount.setTextColor(Color.parseColor("#da552f"));
        }else {
            ivVoteIcon.setImageResource(R.drawable.ic_voteup);
            tvVoteCount.setTextColor(Color.GRAY);
        }

        //Log.d("tech_hunt_adapter", techHunt.getHunter().getSmallProfileImageUrl());
        Picasso.with(getContext()).load(techHunt.getHunter().getSmallProfileImageUrl()).fit().into(ivProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User hunter = techHunt.getHunter();
                if (hunter != null) {
                    int userId = hunter.getId();

                    Intent intent = new Intent(getContext(), UserActivity.class);
                    intent.putExtra(User.USER_ID_MESSAGE, userId);

                    getContext().startActivity(intent);
                }
            }
        });

        return convertView;
    }
}
