package com.example.android.vkgroup.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.Models.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.providers.GroupProvider;
import com.example.android.vkgroup.views.GroupView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.List;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {

    public void loadGroups() {
getViewState().startLoading();
        GroupProvider groupProvider = new GroupProvider(this);
        groupProvider.loadGroups();
    }
    public void groupsLoaded(List<GroupModel>groupModelList){
        getViewState().endLoading();
        if (groupModelList.size() == 0){
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_grous_item);
        }
        else
            getViewState().setupGroupsList(groupModelList);
    }



    public void showError(int textResource){
        getViewState().showError(textResource);
    }
}
