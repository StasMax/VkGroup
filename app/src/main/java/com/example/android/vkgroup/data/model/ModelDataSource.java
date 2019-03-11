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

    public void listDb(List<GroupModel> groupModelList) {
        for (GroupModel groupModel : groupModelList) {
            modelDao.insertAll(groupModel);
        }
    }

    public List<GroupModel> loadLstDb() {
       // groupModelList.addAll(modelDao.getAllList());
        return groupModelList;
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




    @Override
    public void insertListInDb(List<GroupModel> groupModelList) {
        for (GroupModel groupModel : groupModelList) {
            modelDao.insertAll(groupModel);
        }
    }

    @Override
    public Single<List<GroupModel>> loadListDb() {
        return modelDao.getAllList();
    }

    @Override
    public void update(GroupModel groupModel) {
        modelDao.update(groupModel);
    }

    @Override
    public void updateList(List<GroupModel> groupModelList) {
        modelDao.updateList(groupModelList);
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
