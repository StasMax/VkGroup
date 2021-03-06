package com.example.android.vkgroup.data.model;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IModelRepository {

    void insertListInDb(List<GroupModel> groupModelList);

    void update(GroupModel groupModel);

    Flowable<List<GroupModel>> getAll();

    Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite);
}
