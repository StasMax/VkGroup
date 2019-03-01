package com.example.android.vkgroup.provider;


import android.os.Handler;

import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.presenter.FavoritePresenter;
import com.example.android.vkgroup.presenter.GroupPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.android.vkgroup.model.AppDatabase.getINSTANCE;


public class GroupProvider {
    private GroupPresenter gPesenter;
    private FavoritePresenter fPesenter;
    List<GroupModel> testListGroups = new ArrayList<>();

    public GroupProvider(GroupPresenter gPesenter) {
        this.gPesenter = gPesenter;
    }

    public GroupProvider(FavoritePresenter fPesenter) {
        this.fPesenter = fPesenter;
    }

    public void loadGroups() {

        getINSTANCE().getModelDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupModel>>() {
                    @Override
                    public void accept(List<GroupModel> loadGroupList) throws Exception {
                        gPesenter.groupsLoaded(loadGroupList);
                    }
                });

    }

    public void loadFavoriteGroups() {
        getINSTANCE().getModelDao().getByFavorite(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupModel>>() {
                    @Override
                    public void accept(List<GroupModel> loadGroupList) throws Exception {
                        fPesenter.groupsFavoriteLoaded(loadGroupList);
                    }
                });
    }
}
