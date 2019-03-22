package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.presentation.mvp.view.FavoriteView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends BasePresenter<FavoriteView> {
    private GroupInteractor groupInteractor;

    @Inject
    public FavoritePresenter(GroupInteractor groupInteractor) {
        this.groupInteractor = groupInteractor;
    }

    public void onInitFavoriteGroups() {
        addSubscription(groupInteractor.getFavoriteGroups(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onInitGroupsFavoriteRecycle));
    }

    private void onInitGroupsFavoriteRecycle(List<GroupModel> groupModelFavoriteList) {
        if (groupModelFavoriteList.size() == 0) {
            getViewState().setupEmptyList();
        } else
            getViewState().setupGroupsList(groupModelFavoriteList);
    }

}
