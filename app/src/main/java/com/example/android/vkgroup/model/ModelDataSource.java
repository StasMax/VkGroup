package com.example.android.vkgroup.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ModelDataSource implements ModelRepository {

    private List<GroupModel> groupModelList = new ArrayList<>();
    private List<GroupModel> groupModelFavoriteList = new ArrayList<>();
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
    public List<GroupModel> loadFavoriteList() {
       return modelDao.getByFavoriteList(true);
    }

    public void deleteAllDb(List<GroupModel> groupModelList) {
        modelDao.deleteAll(groupModelList);
    }


    public void updateGmList(List<GroupModel> groupModelList) {
        modelDao.updateFavorite(groupModelList);
    }

    public void setFavorite(final GroupModel groupModel) {
        Callable<Void> clb = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                modelDao.update(groupModel);
                return null;
            }
        };
        Disposable completable = Completable.fromCallable(clb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    public void setOutFavorite(final GroupModel groupModel) {
        Callable<Void> clb = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                modelDao.update(groupModel);
                return null;
            }
        };
        Disposable completable = Completable.fromCallable(clb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }
}
