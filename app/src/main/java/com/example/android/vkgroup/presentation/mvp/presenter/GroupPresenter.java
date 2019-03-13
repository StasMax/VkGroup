package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {
    private GroupAdapterRv groupAdapterRv;
    private GroupInteractor groupInteractor;
    private CompositeDisposable disposables;
    private List<GroupModel> favoriteQuery = new ArrayList<>();
    private List<GroupModel> groupModelsQuery = new ArrayList<>();
    private List<GroupModel> groupModelsQueryVk = new ArrayList<>();

    @Inject
    public GroupPresenter(GroupAdapterRv groupAdapterRv, GroupInteractor groupInteractor) {
        this.groupAdapterRv = groupAdapterRv;
        this.groupInteractor = groupInteractor;
        disposables = new CompositeDisposable();
    }

    public void loadGroupsVk() {

        groupInteractor.getFavorite()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposables::add)
                .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                    @Override
                    public void onSuccess(List<GroupModel> groupModels) {
                        favoriteQuery.addAll(groupModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

        groupInteractor.getGroupsListFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposables::add)
                .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                    @Override
                    public void onSuccess(List<GroupModel> groupModels) {
                        groupModelsQuery.addAll(groupModels);
                        groupInteractor.deleteAll(groupModelsQuery);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

        groupInteractor.getAllListGroupsVk()
                .doOnSubscribe(disposable -> {
                    disposables.add(disposable);
                    getViewState().startLoading();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                    @Override
                    public void onSuccess(List<GroupModel> groupModels) {
                        groupModelsQueryVk.addAll(groupModels);
                        insertFavorite(groupModelsQueryVk, favoriteQuery);
                        groupInteractor.insertVkInDb(groupModelsQueryVk);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void loadGroupsFromDb() {

        Disposable disposableFlowable = groupInteractor.getAllGroupsFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::groupsLoaded);
        disposables.add(disposableFlowable);
    }

    private void groupsLoaded(List<GroupModel> groupModelList) {
        getViewState().endLoading();
        if (groupModelList.size() == 0) {
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_groups_item);
        } else
            getViewState().setupGroupsList();
        groupAdapterRv.setupGroups(groupModelList);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.clear();
            disposables.dispose();
        }
        groupInteractor.allDispose();
    }
}
