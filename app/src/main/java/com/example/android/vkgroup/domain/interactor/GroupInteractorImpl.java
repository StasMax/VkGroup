package com.example.android.vkgroup.domain.interactor;

import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.data.model.IModelRepository;

import com.example.android.vkgroup.data.repository.VkRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class GroupInteractorImpl implements IGroupInteractor {

    private VkRepository vkRepository;
    private IModelRepository modelRepository;

    @Inject
    public GroupInteractorImpl(VkRepository vkRepository, IModelRepository modelRepository) {
        this.vkRepository = vkRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public Completable updateFavorite(GroupModel groupModel) {
        return Completable.fromAction(() -> modelRepository.update(groupModel));
    }

    @Override
    public Completable insertVkInDb(List<GroupModel> groupModelsVk) {
        return Completable.fromAction(() -> modelRepository.insertListInDb(groupModelsVk));
    }

    @Override
    public Flowable<List<GroupModel>> getAllGroupsFromDb() {
        return modelRepository.getAll()
                .distinct();
    }

    @Override
    public Flowable<List<GroupModel>> getFavoriteGroups(Boolean isFavorite) {
        return modelRepository.getByFavorite(true);
    }

    @Override
    public Single<List<GroupModel>> getAllListGroupsVk() {
        return vkRepository.getListGroupsSingle();
    }

}