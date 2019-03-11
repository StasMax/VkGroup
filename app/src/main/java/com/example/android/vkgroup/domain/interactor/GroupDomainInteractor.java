package com.example.android.vkgroup.domain.interactor;

import android.util.Log;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.ModelRepository;
import com.example.android.vkgroup.data.repository.DataVkRepository;
import com.example.android.vkgroup.data.repository.VkRepository;
import com.example.android.vkgroup.presentation.app.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GroupDomainInteractor implements GroupInteractor {
    //  @Inject
    VkRepository vkRepository;
    //   @Inject
    ModelRepository modelRepository;

    private Disposable disposable1;
    private Disposable disposable2;
    private Disposable disposable3;
    private Disposable disposable4;
    private Disposable disposable5;


    @Inject
    public GroupDomainInteractor(VkRepository vkRepository, ModelRepository modelRepository) {
        this.vkRepository = vkRepository;
        this.modelRepository = modelRepository;
        App.getComponent().inject(this);

    }


    public Single<List<GroupModel>> getGroupsListFromDb() {
        return modelRepository.loadListDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void updateFavorite(GroupModel groupModel) {
        Callable<Void> cdb = () -> {
            modelRepository.update(groupModel);
            return null;
        };
        disposable2 = Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public void updateFavoriteList(List<GroupModel> groupModels) {
        Callable<Void> cdb = () -> {
            modelRepository.updateList(groupModels);
            return null;
        };
        disposable3 = Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }


    @Override
    public void disposeAll() {
//compositeDisposable.dispose();
    }

    @Override
    public void insertVkInDb(List<GroupModel> groupModels) {
        Callable<Void> cdb = () -> {
            modelRepository.insertListInDb(groupModels);
            return null;
        };
        disposable5 = Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public void deleteAll(List<GroupModel> groupModels) {
        Callable<Void> cdb = () -> {
            modelRepository.deleteAllDb(groupModels);
            return null;
        };
        disposable1 = Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public Flowable<List<GroupModel>> getAllGroupsFromDb() {
        return modelRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<GroupModel>> getFavoriteGroups(Boolean isFavorite) {
        return modelRepository.getByFavorite(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<GroupModel>> getFavorite() {
        return modelRepository.getByFavoriteSingle(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<GroupModel>> getAllListGroupsVk() {
        return vkRepository.getListGroupsSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}