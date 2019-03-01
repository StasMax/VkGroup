package com.example.android.vkgroup.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.provider.GroupProvider;
import com.example.android.vkgroup.view.FavoriteView;

import java.util.List;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteView> {

    public void loadGroups() {
        GroupProvider groupProvider = new GroupProvider(this);
        groupProvider.loadFavoriteGroups();
    }

    public void groupsFavoriteLoaded(List<GroupModel> groupModelFavoriteList) {
        if (groupModelFavoriteList.size() == 0) {
            getViewState().setupEmptyList();
        } else
            getViewState().setupGroupsList(groupModelFavoriteList);
    }
}
