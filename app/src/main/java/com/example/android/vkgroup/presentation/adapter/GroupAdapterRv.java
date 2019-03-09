package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.ModelRepository;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;

public class GroupAdapterRv extends RecyclerView.Adapter<GroupAdapterRv.ViewHolder> {

    private List<GroupModel> mGroupModelList = new ArrayList<>();
    private List<GroupModel> mSourceList = new ArrayList<>();
    @Inject
    ModelRepository modelRepository;
    private Disposable dispCheckBox;

    public Disposable getDispCheckBox() {
        return dispCheckBox;
    }

    public GroupAdapterRv() {
        App.getComponent().inject(this);
    }

    public void setupGroups(List<GroupModel> groupModelList) {
        mSourceList.clear();
        mSourceList.addAll(groupModelList);
        filter("");
    }

    public void setupFavoriteGroups(List<GroupModel> groupModelListFavorite) {
        mGroupModelList.clear();
        mGroupModelList.addAll(groupModelListFavorite);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        mGroupModelList.clear();
        for (GroupModel gModel : mSourceList) {
            if (gModel.getName().contains(query) || gModel.getName().toLowerCase().contains(query.toLowerCase())) {
                mGroupModelList.add(gModel);
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
        viewHolder.bind(mGroupModelList.get(i));
    }

    @Override
    public int getItemCount() {
        return mGroupModelList.size();
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
        private CheckBox mCheckBox;
        private CircleImageView mCivAvatar;
        private TextView mTxtGroupName;
        private TextView mTxtSubscribers;

        public ViewHolder(@NonNull View itemView) {
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

            dispCheckBox = RxView.clicks(mCheckBox).subscribe(o -> {
                if (mCheckBox.isChecked()) {
                    groupModel.setFavorite(true);
                    modelRepository.setFavorite(groupModel);
                } else {
                    groupModel.setFavorite(false);
                    modelRepository.setOutFavorite(groupModel);
                }
            });
            if (groupModel.getAvatar() != null) {
                Picasso.with(itemView.getContext()).load(groupModel.getAvatar()).into(mCivAvatar);
            }
        }
    }
}