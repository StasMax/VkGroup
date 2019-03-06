package com.example.android.vkgroup.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.app.App;

import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.model.ModelDao;
import com.example.android.vkgroup.model.ModelRepository;
import com.example.android.vkgroup.view.FavoriteView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteView> {
    @Inject
    ModelDao mModelDao;
    @Inject
    GroupAdapterRv groupAdapterRv;
    @Inject
    ModelRepository modelRepository;
    private Disposable disposable;

    public FavoritePresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadGroups();
    }

    public void loadGroups() {
        disposable = mModelDao.getByFavorite(true)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::groupsFavoriteLoaded);
    }

    public void groupsFavoriteLoaded(List<GroupModel> groupModelFavoriteList) {
        if (groupModelFavoriteList.size() == 0) {
            getViewState().setupEmptyList();
        } else
            getViewState().setupGroupsList();
        groupAdapterRv.setupFavoriteGroups(groupModelFavoriteList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        groupAdapterRv.getDispCheckBox().dispose();
    }
}
