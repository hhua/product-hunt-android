package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.activities.UserActivity;
import com.hhua.android.producthunt.models.Follower;
import com.hhua.android.producthunt.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowersArrayAdapter extends ArrayAdapter<Follower> {
    public FollowersArrayAdapter(Context context, List<Follower> followers) {
        super(context, android.R.layout.simple_list_item_1, followers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Follower follower = getItem(position);
        final User user = follower.getUser();

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvUserHeadline = (TextView) convertView.findViewById(R.id.tvUserHeadline);

        ivProfile.setImageResource(0);

        tvUserName.setText(user.getName());
        tvUserHeadline.setText(user.getHeadline());

        Picasso.with(getContext()).load(user.getMediumProfileImageUrl()).fit().into(ivProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = user.getId();

                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.putExtra(User.USER_ID_MESSAGE, userId);

                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
