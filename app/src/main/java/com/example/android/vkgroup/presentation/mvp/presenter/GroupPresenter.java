package com.example.android.vkgroup.presentation.mvp.presenter;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.example.android.vkgroup.domain.interactor.IGroupInteractor;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GroupPresenter extends BasePresenter<GroupView> {
    private IGroupInteractor groupInteractor;

    @Inject
    public GroupPresenter(IGroupInteractor groupInteractor) {
        this.groupInteractor = groupInteractor;
    }

    public void loginVk(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                onInitGroupsVk();
            }

            @Override
            public void onError(VKError error) {
                getViewState().showError(R.string.error_login);
            }
        });
    }

    public void onInitGroupsVk() {

        addSubscription(groupInteractor.getAllListGroupsVk()
                .doOnSubscribe(disposable -> getViewState().startLoading())
                .observeOn(Schedulers.io())
                .flatMapCompletable(groupModels -> groupInteractor.insertVkInDb(groupModels))
                .subscribe());
    }

    public void onInitGroupsDb() {
        addSubscription(groupInteractor.getAllGroupsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groupModels -> {
                    onInitGroupsRecycle(groupModels);
                    getViewState().endLoading();
                }));
    }

    private void onInitGroupsRecycle(List<GroupModel> groupModelList) {
        if (groupModelList.size() == 0) {
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_groups_item);
        } else
            getViewState().setupGroupsList(groupModelList);
    }

}
