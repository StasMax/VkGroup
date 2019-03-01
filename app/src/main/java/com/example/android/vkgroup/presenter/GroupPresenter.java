package com.example.android.vkgroup.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.provider.GroupDbProvider;
import com.example.android.vkgroup.provider.GroupProvider;
import com.example.android.vkgroup.view.GroupView;

import java.util.List;

import static com.example.android.vkgroup.helper.Helper.isOnline;
import static com.vk.sdk.VKUIHelper.getApplicationContext;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {

    public void loadGroups() {
        getViewState().startLoading();

        if (isOnline(getApplicationContext())) {
            GroupDbProvider groupDbProvider = new GroupDbProvider(this);
            groupDbProvider.loadGroupToDb();
        }

        GroupProvider groupProvider = new GroupProvider(this);
        groupProvider.loadGroups();
    }

    public void groupsLoaded(List<GroupModel> groupModelList) {
        getViewState().endLoading();
        if (groupModelList.size() == 0) {
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_grous_item);
        } else
            getViewState().setupGroupsList(groupModelList);
    }

    public void showError(int textResource) {
        getViewState().showError(textResource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
