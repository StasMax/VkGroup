package com.example.android.vkgroup.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ModelRepositoryImpl implements IModelRepository {
    private List<GroupModel> listVk = new ArrayList<>();
    private List<GroupModel> listDb = new ArrayList<>();
    private ModelDao modelDao;

    @Inject
    public ModelRepositoryImpl(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    @Override
    public void insertListInDb(List<GroupModel> groupModelList) {
        listVk.clear();
        listDb.clear();
        listVk.addAll(groupModelList);
        listDb.addAll(modelDao.getAllList());

        for (GroupModel groupModel : listVk) {
            if (!(listDb.contains(groupModel))) {
                modelDao.insert(groupModel);
            }
        }
        for (GroupModel groupModel : listDb
        ) {
            if (!(listVk.contains(groupModel))) {
                modelDao.delete(groupModel);
            }
        }
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
    public Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite) {
        return modelDao.getByFavorite(true);
    }
}
