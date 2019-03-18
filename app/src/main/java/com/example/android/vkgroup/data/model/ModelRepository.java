package com.example.android.vkgroup.data.model;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ModelRepository {

    void insertListInDb(List<GroupModel> groupModelList);

    void deleteAllDb(List<GroupModel> groupModelList);

    void clearAllDb();

    Single<List<GroupModel>> loadListDb();

    void update(GroupModel groupModel);

    Flowable<List<GroupModel>> getAll();

    Single<List<GroupModel>> getByFavoriteSingle(Boolean isFavorite);

    Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite);
}
