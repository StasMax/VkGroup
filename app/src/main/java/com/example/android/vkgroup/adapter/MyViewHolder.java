package com.example.android.vkgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.model.ModelRepository;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    @Inject
    public ModelRepository modelRepository;
    private CheckBox mCheckBox;
    private CircleImageView mCivAvatar;
    private TextView mTxtGroupName;
    private TextView mTxtSubscribers;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        mCivAvatar = itemView.findViewById(R.id.groups_siv_avatar);
        mTxtGroupName = itemView.findViewById(R.id.group_txt_name);
        mTxtSubscribers = itemView.findViewById(R.id.group_txt_subscribers);
        mCheckBox = itemView.findViewById(R.id.isFavorite_checkBox);
    }

    public void bind(final GroupModel groupModel) {
        mTxtGroupName.setText(groupModel.getName());
        mTxtSubscribers.setText(groupModel.getSubscribers());

        if (groupModel.getFavorite()) mCheckBox.setChecked(true);
        else mCheckBox.setChecked(false);

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    groupModel.setFavorite(true);
                    modelRepository.setFavorite(groupModel);
                } else {
                    groupModel.setFavorite(false);
                    modelRepository.setOutFavorite(groupModel);
                }
            }
        });

        if (groupModel.getAvatar() != null) {
            Picasso.with(itemView.getContext()).load(groupModel.getAvatar()).into(mCivAvatar);
        }
    }
}