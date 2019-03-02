package com.example.android.vkgroup.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.adapter.GroupAdapter;
import com.example.android.vkgroup.model.AppDatabase;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.view.FavoriteView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteView> {
    @Inject
    public AppDatabase providesRoomDatabase;
    @Inject
    public GroupAdapter groupAdapter;
    private Disposable disposable;

    public void loadGroups() {
        disposable = providesRoomDatabase.getModelDao().getByFavorite(true)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupModel>>() {
                    @Override
                    public void accept(List<GroupModel> loadGroupList) throws Exception {
                        groupsFavoriteLoaded(loadGroupList);
                    }
                });
    }

    public void groupsFavoriteLoaded(List<GroupModel> groupModelFavoriteList) {
        if (groupModelFavoriteList.size() == 0) {
            getViewState().setupEmptyList();
        } else
            getViewState().setupGroupsList();
        groupAdapter.setupFavoriteGroups(groupModelFavoriteList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
