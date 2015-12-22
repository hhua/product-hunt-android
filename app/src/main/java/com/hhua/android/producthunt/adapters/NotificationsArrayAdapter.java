package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.models.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationsArrayAdapter extends ArrayAdapter<Notification> {

    public NotificationsArrayAdapter(Context context, List<Notification> notifications) {
        super(context, android.R.layout.simple_list_item_1, notifications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Notification notification = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
        }

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvNotificationBody = (TextView) convertView.findViewById(R.id.tvNotificationBody);
        TextView tvNotificationCreatedAt = (TextView) convertView.findViewById(R.id.tvNotificationCreatedAt);

        ivProfile.setImageResource(0);

        tvNotificationBody.setText(buildNotificationBody(notification));
        tvNotificationCreatedAt.setText(notification.getCreatedAt());

        Picasso.with(getContext()).load(notification.getFromUser().getSmallProfileImageUrl()).fit().into(ivProfile);

        return convertView;
    }

    private String buildNotificationBody(Notification notification){
        return notification.getSentence();
    }
}
