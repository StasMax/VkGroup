package com.example.android.vkgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.R;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<GroupModel> mGroupModelList = new ArrayList<>();
    private List<GroupModel> mSourseList = new ArrayList<>();

    public void setupGroups(List<GroupModel> groupModelList) {
        mSourseList.clear();
        mSourseList.addAll(groupModelList);
        filter("");
    }

    public void setupFavoriteGroups(List<GroupModel> groupModelList) {
        mGroupModelList.clear();
        mGroupModelList.addAll(groupModelList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        mGroupModelList.clear();
        for (GroupModel gModel : mSourseList) {
            if (gModel.getName().contains(query) || gModel.getName().contains(query.toLowerCase())) {
                mGroupModelList.add(gModel);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_group, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.bind(mGroupModelList.get(i));
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
}
