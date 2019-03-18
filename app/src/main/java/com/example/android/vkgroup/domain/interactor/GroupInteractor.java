package com.example.android.vkgroup.domain.interactor;

import com.example.android.vkgroup.data.model.GroupModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface GroupInteractor {
    Single<List<GroupModel>> getFavorite();

    Single<List<GroupModel>> getAllListGroupsVk();

    Completable updateFavorite(GroupModel groupModel);

    Completable insertVkInDb(List<GroupModel> groupModelsVk);

    Completable clearAll();

    Flowable<List<GroupModel>> getAllGroupsFromDb();

    Flowable<List<GroupModel>> getFavoriteGroups(Boolean isFavorite);

}