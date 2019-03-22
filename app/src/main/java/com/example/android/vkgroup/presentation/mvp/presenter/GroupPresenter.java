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

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GroupPresenter extends BasePresenter<GroupView> {
    private IGroupInteractor groupInteractor;
    private List<GroupModel> favoriteQuery = new ArrayList<>();
    private List<GroupModel> groupModelsQueryVk = new ArrayList<>();

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
        favoriteQuery.clear();
        groupModelsQueryVk.clear();
        addSubscription(groupInteractor.getFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteQuery::addAll, Throwable::printStackTrace));

        addSubscription(groupInteractor.getAllListGroupsVk()
                .doOnSubscribe(disposable -> getViewState().startLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groupModels -> {
                    groupModelsQueryVk.addAll(groupModels);
                    insertFavorite(groupModelsQueryVk, favoriteQuery);

                    addSubscription(groupInteractor.insertVkInDb(groupModelsQueryVk)
                            .subscribeOn(Schedulers.io())
                            .subscribe());
                }));
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

    private void insertFavorite(List<GroupModel> groupModelsQueryVk, List<GroupModel> favoriteQuery) {
        for (int i = 0; i < groupModelsQueryVk.size(); i++) {
            for (int j = 0; j < favoriteQuery.size(); j++) {
                if (favoriteQuery.get(j).equals(groupModelsQueryVk.get(i))) {
                    groupModelsQueryVk.get(i).setFavorite(favoriteQuery.get(j).getFavorite());
                }
            }
        }
    }

}
