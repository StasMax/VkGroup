package com.example.android.vkgroup.data.model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ModelDataSource implements ModelRepository {
    private ModelDao modelDao;

    @Inject
    public ModelDataSource(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    @Override
    public void insertListInDb(List<GroupModel> groupModelList) {
        for (GroupModel groupModel : groupModelList) {
            modelDao.insertAll(groupModel);
        }
    }

    @Override
    public void clearAllDb() {
        modelDao.clearTable();
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
