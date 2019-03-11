package com.example.android.vkgroup.domain.interactor;

import android.view.LayoutInflater;

import com.example.android.vkgroup.data.model.GroupModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface GroupInteractor {
    Single<List<GroupModel>> getFavorite();

    Single<List<GroupModel>> getAllListGroupsVk();

    Single<List<GroupModel>> getGroupsListFromDb();

    void updateFavorite(GroupModel groupModel);

    void updateFavoriteList(List<GroupModel> groupModels);

    void disposeAll();

    void insertVkInDb(List<GroupModel> groupModels);

    void deleteAll(List<GroupModel> groupModels);

    Flowable<List<GroupModel>> getAllGroupsFromDb();

    Flowable<List<GroupModel>> getFavoriteGroups(Boolean isFavorite);

    List<GroupModel> vkList();
}
