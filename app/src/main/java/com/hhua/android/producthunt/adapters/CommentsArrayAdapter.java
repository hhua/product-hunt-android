package com.hhua.android.producthunt.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hhua.android.producthunt.R;
import com.hhua.android.producthunt.activities.UserActivity;
import com.hhua.android.producthunt.models.Comment;
import com.hhua.android.producthunt.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahua on 12/18/15.
 */
public class CommentsArrayAdapter extends ArrayAdapter<Comment> {

    public CommentsArrayAdapter(Context context, List<Comment> comments) {
        super(context, android.R.layout.simple_list_item_1, comments);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Comment comment = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        TextView tvCommenterDescription = (TextView) convertView.findViewById(R.id.tvCommenterDescription);
        TextView tvCommentBody = (TextView) convertView.findViewById(R.id.tvCommentBody);
        TextView tvCommentCreatedAt = (TextView) convertView.findViewById(R.id.tvCommentCreatedAt);

        String description = "<b>" + comment.getUser().getName() + "</b> " + " - " + comment.getUser().getHeadline();
        tvCommenterDescription.setText(Html.fromHtml(description));
        tvCommentBody.setText(Html.fromHtml(comment.getBody()));
        tvCommentCreatedAt.setText(comment.getCreatedAt());

        ImageView ivCommenterAvatar = (ImageView) convertView.findViewById(R.id.ivCommenterAvatar);
        ivCommenterAvatar.setImageResource(0);

        Picasso.with(getContext()).load(comment.getUser().getMediumProfileImageUrl()).fit().into(ivCommenterAvatar);
        ivCommenterAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User commenter = comment.getUser();
                if (commenter != null) {
                    int userId = commenter.getId();
                    Intent intent = new Intent(getContext(), UserActivity.class);
                    intent.putExtra(User.USER_ID_MESSAGE, userId);

                    parent.getContext().startActivity(intent);
                }
            }
        });

        ListView lvChildComments = (ListView) convertView.findViewById(R.id.lvChildComments);
        CommentsArrayAdapter commentsArrayAdapter = new CommentsArrayAdapter(getContext(), comment.getChildComments());
        lvChildComments.setAdapter(commentsArrayAdapter);

        return convertView;
    }
}
