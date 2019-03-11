package com.example.android.vkgroup.data.model;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ModelRepository {

    void listDb(List<GroupModel> groupModelList);

    List<GroupModel> loadLstDb();

    void deleteAllDb(List<GroupModel> groupModelList);

    void setFavorite(GroupModel groupModel);

    void setOutFavorite(GroupModel groupModel);



    void insertListInDb(List<GroupModel> groupModelList);

    Single<List<GroupModel>> loadListDb();

    void update(GroupModel groupModel);

    void updateList(List<GroupModel> groupModelList);

    Flowable<List<GroupModel>> getAll();

    Single<List<GroupModel>> getByFavoriteSingle(Boolean isFavorite);

    Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite);
}
