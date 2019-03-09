package com.example.android.vkgroup.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ModelDataSource implements ModelRepository {

    private List<GroupModel> groupModelList = new ArrayList<>();
    private ModelDao modelDao;

    @Inject
    public ModelDataSource(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    @Override
    public void listDb(List<GroupModel> groupModelList) {
        for (GroupModel groupModel : groupModelList) {
            modelDao.insertAll(groupModel);
        }
    }
    @Override
    public List<GroupModel> loadLstDb() {
        groupModelList.addAll(modelDao.getAllList());
        return groupModelList;
    }
    @Override
    public void deleteAllDb(List<GroupModel> groupModelList) {
        modelDao.deleteAll(groupModelList);
    }

    @Override
    public void update(GroupModel groupModel) {
        modelDao.update(groupModel);
    }

    @Override
    public Flowable<List<GroupModel>> getAll() {
        return modelDao.getAll();
    }

    @Override
    public Single<List<GroupModel>> getByFavoriteSingle(Boolean isFavorite) {
        return modelDao.getByFavoriteSingle(true);
    }

    @Override
    public Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite) {
        return modelDao.getByFavorite(true);
    }
}