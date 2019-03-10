package com.example.android.vkgroup.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class ModelDataSource implements ModelRepository {

    private List<GroupModel> groupModelList = new ArrayList<>();
    private ModelDao modelDao;

    @Inject
    public ModelDataSource(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    public void listDb(List<GroupModel> groupModelList) {
        for (GroupModel groupModel : groupModelList) {
            modelDao.insertAll(groupModel);
        }
    }

    public List<GroupModel> loadLstDb() {
        groupModelList.addAll(modelDao.getAllList());
        return groupModelList;
    }

    @Override
    public Flowable<List<GroupModel>> getAll() {
        return modelDao.getAll();
    }

   public void deleteAllDb(List<GroupModel> groupModelList) {
        modelDao.deleteAll(groupModelList);
    }

    public void setFavorite(final GroupModel groupModel) {
        Callable<Void> clb = () -> {
            modelDao.update(groupModel);
            return null;
        };
        Completable.fromCallable(clb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    public void setOutFavorite(final GroupModel groupModel) {
        Callable<Void> clb = () -> {
            modelDao.update(groupModel);
            return null;
        };
        Completable.fromCallable(clb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }
}
