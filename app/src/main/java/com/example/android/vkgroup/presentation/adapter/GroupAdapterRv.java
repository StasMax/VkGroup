package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.data.model.GroupModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;

public class GroupAdapterRv extends RecyclerView.Adapter<GroupAdapterRv.ViewHolder> {

    private List<GroupModel> groupModelList = new ArrayList<>();
    private List<GroupModel> sourceList = new ArrayList<>();
    @Inject
    GroupInteractor groupInteractor;
    private Disposable dispCheckBox;

    public Disposable getDispCheckBox() {
        return dispCheckBox;
    }

    public GroupAdapterRv() {
        App.getComponent().inject(this);
    }

    public void setupGroups(List<GroupModel> groupModelList) {
        sourceList.clear();
        sourceList.addAll(groupModelList);
        filter("");
    }

    public void setupFavoriteGroups(List<GroupModel> groupModelListFavorite) {
        groupModelList.clear();
        groupModelList.addAll(groupModelListFavorite);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        groupModelList.clear();
        for (GroupModel gModel : sourceList) {
            if (gModel.getName().contains(query) || gModel.getName().toLowerCase().contains(query.toLowerCase())) {
                groupModelList.add(gModel);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_group, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return groupModelList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

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

        public void bind(final int position) {
            txtGroupName.setText(groupModelList.get(position).getName());
            txtSubscribers.setText(groupModelList.get(position).getSubscribers());
            if (groupModelList.get(position).getFavorite()) checkBox.setChecked(true);
            else checkBox.setChecked(false);

            dispCheckBox = RxView.clicks(checkBox).subscribe(o -> {
                if (checkBox.isChecked()) {
                    groupModelList.get(position).setFavorite(true);
                    groupInteractor.updateFavorite(groupModelList.get(position));
                } else {
                    groupModelList.get(position).setFavorite(false);
                    groupInteractor.updateFavorite(groupModelList.get(position));
                }
            });
            if (groupModelList.get(position).getAvatar() != null) {
                Picasso.with(itemView.getContext()).load(groupModelList.get(position).getAvatar()).into(civAvatar);
            }
        }
    }
}
