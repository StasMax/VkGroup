package com.example.android.vkgroup.data.model;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ModelRepository {

    void insertListInDb(List<GroupModel> groupModelList);

    Single<List<GroupModel>> loadListDb();

    void deleteAllDb(List<GroupModel> groupModelList);

    void update(GroupModel groupModel);

    void updateList(List<GroupModel> groupModelList);

    Flowable<List<GroupModel>> getAll();

    Single<List<GroupModel>> getByFavoriteSingle(Boolean isFavorite);

    Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite);
}
