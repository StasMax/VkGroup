package com.example.android.vkgroup.helper;

import android.support.v7.util.DiffUtil;

import com.example.android.vkgroup.model.GroupModel;

import java.util.List;

public class GroupModelDiffUtilCallback extends DiffUtil.Callback {

    private final List<GroupModel> oldList;
    private final List<GroupModel> newList;

    public GroupModelDiffUtilCallback(List<GroupModel> oldList, List<GroupModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        GroupModel oldProduct = oldList.get(oldItemPosition);
        GroupModel newProduct = newList.get(newItemPosition);
        return oldProduct.getId() == newProduct.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        GroupModel oldProduct = oldList.get(oldItemPosition);
        GroupModel newProduct = newList.get(newItemPosition);
        return oldProduct.getSubscribers().equals(newProduct.getSubscribers())
                && oldProduct.getFavorite() == newProduct.getFavorite();
    }
}