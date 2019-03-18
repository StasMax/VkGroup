package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.ModelDao;
import com.example.android.vkgroup.data.model.ModelRepository;
import com.example.android.vkgroup.presentation.mvp.view.FavoriteView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends BasePresenter<FavoriteView> {
    @Inject
    GroupInteractor groupInteractor;

    @Inject
    public FavoritePresenter(GroupInteractor groupInteractor) {
        this.groupInteractor = groupInteractor;
    }

    public void loadGroups() {
        addSubscription(groupInteractor.getFavoriteGroups(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::groupsFavoriteLoaded));
    }

    private void groupsFavoriteLoaded(List<GroupModel> groupModelFavoriteList) {
        if (groupModelFavoriteList.size() == 0) {
            getViewState().setupEmptyList();
        } else
            getViewState().setupGroupsList(groupModelFavoriteList);
    }

}
