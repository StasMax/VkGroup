package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.app.App;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.schedulers.Schedulers;

public class ViewHolder extends RecyclerView.ViewHolder {
    private CheckBox checkBox;
    private CircleImageView civAvatar;
    private TextView txtGroupName;
    private TextView txtSubscribers;
    @Inject
    GroupInteractor groupInteractor;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        App.getComponent().inject(this);
        civAvatar = itemView.findViewById(R.id.groups_siv_avatar);
        txtGroupName = itemView.findViewById(R.id.group_txt_name);
        txtSubscribers = itemView.findViewById(R.id.group_txt_subscribers);
        checkBox = itemView.findViewById(R.id.isFavorite_checkBox);
    }

    public void bind(final GroupModel groupModel) {
        txtGroupName.setText(groupModel.getName());
        txtSubscribers.setText(groupModel.getSubscribers());
        if (groupModel.getFavorite())
            checkBox.setChecked(true);
        else checkBox.setChecked(false);

     /*   checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                groupModel.setFavorite(true);
                groupInteractor.updateFavorite(groupModel)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
            } else {
                groupModel.setFavorite(false);
                groupInteractor.updateFavorite(groupModel)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
            }
        });*/
        if (groupModel.getAvatar() != null) {
            Picasso.with(itemView.getContext()).load(groupModel.getAvatar()).into(civAvatar);
        }
    }
}
