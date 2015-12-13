package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.models.Collection;

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

        return convertView;
    }
}
