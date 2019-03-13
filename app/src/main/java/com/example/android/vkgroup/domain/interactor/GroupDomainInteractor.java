package com.example.android.vkgroup.domain.interactor;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.ModelRepository;

import com.example.android.vkgroup.data.repository.VkRepository;
import com.example.android.vkgroup.presentation.app.App;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GroupDomainInteractor implements GroupInteractor {

    private VkRepository vkRepository;
    private ModelRepository modelRepository;
    private final CompositeDisposable disposables;

    @Inject
    public GroupDomainInteractor(VkRepository vkRepository, ModelRepository modelRepository) {
        this.vkRepository = vkRepository;
        this.modelRepository = modelRepository;
        App.getComponent().inject(this);
        disposables = new CompositeDisposable();
    }

    public Single<List<GroupModel>> getGroupsListFromDb() {
        return modelRepository.loadListDb()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void updateFavorite(GroupModel groupModel) {
        Completable.fromAction(() -> modelRepository.update(groupModel))
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposables::add)
                .subscribe();
    }

    @Override
    public void insertVkInDb(List<GroupModel> groupModelsVk) {
        Completable.fromAction(() -> modelRepository.insertListInDb(groupModelsVk))
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposables::add)
                .subscribe();
    }

    @Override
    public void deleteAll(List<GroupModel> groupModels) {
        Completable.fromAction(() -> modelRepository.deleteAllDb(groupModels))
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposables::add)
                .subscribe();
    }

    @Override
    public Flowable<List<GroupModel>> getAllGroupsFromDb() {
        return modelRepository.getAll()
                .subscribeOn(Schedulers.io())
                .distinct();
    }

    @Override
    public Flowable<List<GroupModel>> getFavoriteGroups(Boolean isFavorite) {
        return modelRepository.getByFavorite(true)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<GroupModel>> getFavorite() {
        return modelRepository.getByFavoriteSingle(true)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<GroupModel>> getAllListGroupsVk() {
        return vkRepository.getListGroupsSingle()
                .subscribeOn(Schedulers.io());
    }

    public void allDispose() {
        if (disposables.isDisposed()) {
            disposables.clear();
            disposables.dispose();
        }
    }
}