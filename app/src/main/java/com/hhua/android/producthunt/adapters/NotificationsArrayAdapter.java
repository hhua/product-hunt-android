package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        tvNotificationCreatedAt.setText(notification.getCreatedAt());

        // Set notification body
        String[] bodyPieces = notification.getSentence().split(notification.getBody());
        if (bodyPieces.length != 2){
            tvNotificationBody.setText(notification.getSentence());
        }else{
            String notificationBody = "<b>" + bodyPieces[0] + "</b>" + notification.getBody() + bodyPieces[1];
            tvNotificationBody.setText(Html.fromHtml(notificationBody));
        }

        // load profile image
        Picasso.with(getContext()).load(notification.getFromUser().getSmallProfileImageUrl()).fit().into(ivProfile);

        if(notification.isSeen()){
            LinearLayout itemNotification = (LinearLayout) convertView.findViewById(R.id.itemNotification);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Drawable drawable = new ColorDrawable(0x90ffffff);
                itemNotification.setForeground(drawable);
            }
        }

        return convertView;
    }
}
