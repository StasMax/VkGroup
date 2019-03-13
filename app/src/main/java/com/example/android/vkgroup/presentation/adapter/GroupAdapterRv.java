package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.model.GroupModel;

import java.util.ArrayList;
import java.util.List;


public class GroupAdapterRv extends RecyclerView.Adapter<ViewHolder> {

    private List<GroupModel> groupModelList = new ArrayList<>();
    private List<GroupModel> sourceList = new ArrayList<>();

    public List<GroupModel> getGroupModelList() {
        return groupModelList;
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
        viewHolder.bind(groupModelList.get(i));
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
}
