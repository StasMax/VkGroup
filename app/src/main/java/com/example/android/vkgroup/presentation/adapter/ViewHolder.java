package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.app.App;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import de.hdodenhof.circleimageview.CircleImageView;


public class ViewHolder extends RecyclerView.ViewHolder {
    private CheckBox checkBox;
    private CircleImageView civAvatar;
    private TextView txtGroupName;
    private TextView txtSubscribers;
    @Inject
    GroupInteractor groupInteractor;
    @Inject
    GroupAdapterRv groupAdapterRv;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        App.getComponent().inject(this);
        civAvatar = itemView.findViewById(R.id.groups_siv_avatar);
        txtGroupName = itemView.findViewById(R.id.group_txt_name);
        txtSubscribers = itemView.findViewById(R.id.group_txt_subscribers);
        checkBox = itemView.findViewById(R.id.isFavorite_checkBox);
    }

    public void bind(final int position) {
        txtGroupName.setText(groupAdapterRv.getGroupModelList().get(position).getName());
        txtSubscribers.setText(groupAdapterRv.getGroupModelList().get(position).getSubscribers());
        if (groupAdapterRv.getGroupModelList().get(position).getFavorite())
            checkBox.setChecked(true);
        else checkBox.setChecked(false);

        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                groupAdapterRv.getGroupModelList().get(position).setFavorite(true);
                groupInteractor.updateFavorite(groupAdapterRv.getGroupModelList().get(position));
            } else {
                groupAdapterRv.getGroupModelList().get(position).setFavorite(false);
                groupInteractor.updateFavorite(groupAdapterRv.getGroupModelList().get(position));
            }
        });
        if (groupAdapterRv.getGroupModelList().get(position).getAvatar() != null) {
            Picasso.with(itemView.getContext()).load(groupAdapterRv.getGroupModelList().get(position).getAvatar()).into(civAvatar);
        }
    }
}
