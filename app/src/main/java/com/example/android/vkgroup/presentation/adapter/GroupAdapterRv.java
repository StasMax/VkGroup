package com.example.android.vkgroup.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.model.GroupModel;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapterRv extends RecyclerView.Adapter<ViewHolder> {
    private List<GroupModel> groupsModelList = new ArrayList<>();
    private List<GroupModel> sourceList = new ArrayList<>();

    private Listener listener;

    public interface Listener {
        void onClick(GroupModel groupModel, boolean isChecked);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setupGroups(List<GroupModel> groupModelList) {
        groupsModelList.clear();
        sourceList.clear();
        sourceList.addAll(groupModelList);
        filter("");
    }

    public void setupFavoriteGroups(List<GroupModel> groupModelListFavorite) {
        groupsModelList.clear();
        groupsModelList.addAll(groupModelListFavorite);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        groupsModelList.clear();
        for (GroupModel gModel : sourceList) {
            if (gModel.getName().contains(query) || gModel.getName().toLowerCase().contains(query.toLowerCase())) {
                groupsModelList.add(gModel);
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
        viewHolder.bind(groupsModelList.get(i));
        CheckBox checkBox = viewHolder.itemView.findViewById(R.id.isFavorite_checkBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onClick(groupsModelList.get(i), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupsModelList.size();
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
