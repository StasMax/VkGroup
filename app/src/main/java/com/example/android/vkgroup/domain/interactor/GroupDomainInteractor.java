package com.example.android.vkgroup.domain.interactor;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.ModelDao;
import com.example.android.vkgroup.data.model.ModelRepository;
import com.example.android.vkgroup.data.repository.VkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GroupDomainInteractor implements GroupInteractor{
    VkRepository vkRepository;
    @Inject
    ModelRepository modelRepository;
    CompositeDisposable compositeDisposable;
    @Inject
    ModelDao modelDao;
    private Disposable singleGetFavorite;


    private List<GroupModel> queryDbList;
    private List<GroupModel> queryDbListFavorite;

    public GroupDomainInteractor(VkRepository vkRepository, ModelRepository modelRepository) {
        this.vkRepository = vkRepository;
        this.modelRepository = modelRepository;
            }

    public void getGroupsListFromDb(){
        Callable<Void> cdb = () -> {
            queryDbList = modelRepository.loadLstDb();
           /* if (queryDbList != null) {
                modelRepository.deleteAllDb(queryDbList);
            }*/
            return null;
        };
        Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public void updateFavorite(GroupModel groupModel) {
        Callable<Void> cdb = () -> {
            modelRepository.update(groupModel);
            return null;
        };
        Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }


    @Override
    public void getFavorite() {
        modelRepository.getByFavoriteSingle(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                    @Override
                    public void onSuccess(List<GroupModel> groupModelList) {
                       // queryDbListFavorite.addAll(groupModelList);
                        queryDbListFavorite = groupModelList;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void getAllListGroupsVk(){
        Callable<Void> cdb = () -> {
            vkRepository.getListGroups();
            return null;
        };
        Completable.fromCallable(cdb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }
}
