package com.example.android.vkgroup.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.providers.GroupProvider;
import com.example.android.vkgroup.views.FavoriteView;

import java.util.List;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteView> {

    public void loadGroups() {
        GroupProvider groupProvider = new GroupProvider(this);
        groupProvider.loadFavoriteGroups();
    }

    public void groupsFavoriteLoaded(List<GroupModel> groupModelFavoriteList){

        if (groupModelFavoriteList.size() == 0){
            getViewState().setupEmptyList();

        }
        else
            getViewState().setupGroupsList(groupModelFavoriteList);
    }
}
