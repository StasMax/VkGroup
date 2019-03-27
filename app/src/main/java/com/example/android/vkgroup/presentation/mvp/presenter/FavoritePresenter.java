package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.android.vkgroup.domain.interactor.IGroupInteractor;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.presentation.mvp.view.FavoriteView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends BasePresenter<FavoriteView> {
    private IGroupInteractor groupInteractor;

    @Inject
    public FavoritePresenter(IGroupInteractor groupInteractor) {
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

    public void onSetFavorite(GroupModel groupModel, boolean isChecked) {
        if (isChecked) {
            groupModel.setFavorite(true);
        } else {
            groupModel.setFavorite(false);
        }
        addSubscription(groupInteractor.updateFavorite(groupModel)
                .subscribeOn(Schedulers.newThread())
                .subscribe());
    }

}
