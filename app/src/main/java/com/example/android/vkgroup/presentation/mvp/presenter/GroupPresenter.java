package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GroupPresenter extends BasePresenter<GroupView> {
    private GroupInteractor groupInteractor;
    private List<GroupModel> favoriteQuery = new ArrayList<>();
    private List<GroupModel> groupModelsQueryVk = new ArrayList<>();

    @Inject
    public GroupPresenter(GroupInteractor groupInteractor) {
        this.groupInteractor = groupInteractor;
    }

    public void loadGroupsVk() {
        addSubscription(groupInteractor.getFavorite()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteQuery::addAll, Throwable::printStackTrace));

        addSubscription(groupInteractor.clearAll()
                .subscribeOn(Schedulers.newThread())
                .subscribe());

        addSubscription(groupInteractor.getAllListGroupsVk()
                .doOnSubscribe(disposable -> getViewState().startLoading())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groupModels -> {
                    groupModelsQueryVk.addAll(groupModels);
                    insertFavorite(groupModelsQueryVk, favoriteQuery);
                    groupInteractor.insertVkInDb(groupModelsQueryVk)
                            .subscribeOn(Schedulers.newThread())
                            .subscribe();
                }));
    }

    public void loadGroupsFromDb() {
        addSubscription(groupInteractor.getAllGroupsFromDb()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::groupsLoaded));
    }

    private void groupsLoaded(List<GroupModel> groupModelList) {
        getViewState().endLoading();
        if (groupModelList.size() == 0) {
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_groups_item);
        } else
            getViewState().setupGroupsList(groupModelList);

    }

    private void insertFavorite(List<GroupModel> groupModelsQueryVk, List<GroupModel> favoriteQuery) {
        for (int i = 0; i < groupModelsQueryVk.size(); i++) {
            for (int j = 0; j < favoriteQuery.size(); j++) {
                if (favoriteQuery.get(j).equals(groupModelsQueryVk.get(i))) {
                    groupModelsQueryVk.get(i).setFavorite(favoriteQuery.get(j).getFavorite());
                }
            }
        }
    }

}
