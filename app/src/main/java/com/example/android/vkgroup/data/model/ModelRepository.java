package com.example.android.vkgroup.data.model;

import java.util.List;

import io.reactivex.Flowable;

public interface ModelRepository {

    void listDb(List<GroupModel> groupModelList);

    List<GroupModel> loadLstDb();

    void deleteAllDb(List<GroupModel> groupModelList);

    void setFavorite(GroupModel groupModel);

    void setOutFavorite(GroupModel groupModel);
    Flowable<List<GroupModel>> getAll();
}
