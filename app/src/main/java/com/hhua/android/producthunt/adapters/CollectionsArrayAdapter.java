package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.models.Collection;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class CollectionsArrayAdapter extends ArrayAdapter<Collection> {
    public CollectionsArrayAdapter(Context context, List<Collection> collections){
        super(context, android.R.layout.simple_list_item_1, collections);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Collection collection = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_collection, parent, false);
        }

        TextView tvCollectionName = (TextView) convertView.findViewById(R.id.tvCollectionName);

        tvCollectionName.setText(collection.getName());
        convertView.setBackgroundResource(0); // clear previous resource

        if (collection.getBackgroundImageUrl() != null){
            final View finalConvertView = convertView;
            Picasso.with(getContext()).load(collection.getBackgroundImageUrl()).resize(500, 260).centerCrop().into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    finalConvertView.setBackground(new BitmapDrawable(getContext().getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d("COLLECTION_BITMAP", "FAILED");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.d("COLLECTION_BITMAP", "Prepare Load");
                }
            });
        }

        return convertView;
    }
}
