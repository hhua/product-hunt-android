package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.models.Media;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahua on 12/17/15.
 */
public class MediaArrayAdapter extends ArrayAdapter<Media> {
    public MediaArrayAdapter(Context context, List<Media> mediaList) {
        super(context, android.R.layout.simple_list_item_1, mediaList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Media media = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_media, parent, false);
        }

        ImageView ivMedia = (ImageView) convertView.findViewById(R.id.ivMedia);
        ivMedia.setImageResource(R.color.transparent);

        if (media.getType().equals("image")) {
            Picasso.with(getContext()).load(media.getImageUrl()).fit().into(ivMedia);
        }

        return convertView;
    }
}
