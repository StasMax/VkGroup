package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.model.GroupModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private CheckBox checkBox;
    private CircleImageView civAvatar;
    private TextView txtGroupName;
    private TextView txtSubscribers;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        civAvatar = itemView.findViewById(R.id.groups_siv_avatar);
        txtGroupName = itemView.findViewById(R.id.group_txt_name);
        txtSubscribers = itemView.findViewById(R.id.group_txt_subscribers);
        checkBox = itemView.findViewById(R.id.isFavorite_checkBox);
    }

    public void bind(final GroupModel groupModel) {
        txtGroupName.setText(groupModel.getName());
        txtSubscribers.setText(groupModel.getSubscribers());
        if (groupModel.getFavorite()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        if (groupModel.getAvatar() != null) {
            Picasso.with(itemView.getContext()).load(groupModel.getAvatar()).into(civAvatar);
        }
    }
}
