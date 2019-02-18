package com.example.android.vkgroup.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.models.AppDatabase;
import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.providers.GroupDbProvider;
import com.example.android.vkgroup.providers.GroupProvider;
import com.example.android.vkgroup.views.GroupView;
import java.util.List;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {

    AppDatabase db;

    public void loadGroups() {
        getViewState().startLoading();



        GroupDbProvider groupDbProvider = new GroupDbProvider(this);
        groupDbProvider.loadGroupToDb();

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
