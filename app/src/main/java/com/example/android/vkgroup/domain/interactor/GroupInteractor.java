package com.example.android.vkgroup.domain.interactor;

import com.example.android.vkgroup.data.model.GroupModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface GroupInteractor {
    Single<List<GroupModel>> getFavorite();

    Single<List<GroupModel>> getAllListGroupsVk();

    Single<List<GroupModel>> getGroupsListFromDb();

    void updateFavorite(GroupModel groupModel);

    void insertVkInDb(List<GroupModel> groupModelsVk);

    void deleteAll(List<GroupModel> groupModels);

    Flowable<List<GroupModel>> getAllGroupsFromDb();

    Flowable<List<GroupModel>> getFavoriteGroups(Boolean isFavorite);


}