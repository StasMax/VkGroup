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
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {
    private GroupAdapterRv groupAdapterRv;
    private GroupInteractor groupInteractor;
    private Disposable disposable;
    private List<GroupModel> favoriteQuery = new ArrayList<>();
    private List<GroupModel> groupModelsQuery = new ArrayList<>();
    private List<GroupModel> groupModelsQueryVk = new ArrayList<>();

    @Inject
    public GroupPresenter(GroupAdapterRv groupAdapterRv, GroupInteractor groupInteractor) {
        this.groupAdapterRv = groupAdapterRv;
        this.groupInteractor = groupInteractor;
    }

    public void loadGroupsVk() {

            groupInteractor.getAllListGroupsVk()
                    .doOnSubscribe(disposable -> getViewState().startLoading())
                    .doOnSubscribe(disposable1 -> groupInteractor.getFavorite()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                                @Override
                                public void onSuccess(List<GroupModel> groupModels) {
                                    favoriteQuery.addAll(groupModels);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }))
                    .doOnSubscribe(disposable2 -> groupInteractor.getGroupsListFromDb()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                                @Override
                                public void onSuccess(List<GroupModel> groupModels) {
                                    groupModelsQuery.addAll(groupModels);
                                    groupInteractor.deleteAll(groupModelsQuery);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                        @Override
                        public void onSuccess(List<GroupModel> groupModels) {
                            groupModelsQueryVk.addAll(groupModels);

                            for (int i = 0; i < groupModelsQueryVk.size(); i++) {
                                for (int j = 0; j < favoriteQuery.size(); j++) {
                                    if (favoriteQuery.get(j).equals(groupModelsQueryVk.get(i))) {
                                        groupModelsQueryVk.get(i).setFavorite(favoriteQuery.get(j).getFavorite());
                                    }
                                }
                            }
                            groupInteractor.insertVkInDb(groupModelsQueryVk);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }

    public void loadGroupsFromDb() {
        disposable = groupInteractor.getAllGroupsFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::groupsLoaded);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        groupAdapterRv.getDispCheckBox().dispose();
    }
}
