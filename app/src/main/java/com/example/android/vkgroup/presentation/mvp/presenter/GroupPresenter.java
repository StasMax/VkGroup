package com.example.android.vkgroup.presentation.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.data.model.AppDatabase;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

import static com.example.android.vkgroup.presentation.helper.Helper.isOnline;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {
    @Inject
    GroupAdapterRv groupAdapterRv;
    @Inject
    Context appContext;
    @Inject
    AppDatabase providesRoomDatabase;
    @Inject
    GroupInteractor groupInteractor;
    private List<GroupModel> queryDbList;
    private List<GroupModel> queryDbListFavorite;
    private List<GroupModel> listVk;
    private Disposable disposable;

    public GroupPresenter() {
        App.getComponent().inject(this);
        queryDbList = new ArrayList<>();
        queryDbListFavorite = new ArrayList<>();
        listVk = new ArrayList<>();
    }

    public void loadGroupsVk() {

        if (isOnline(appContext)) {
            getViewState().startLoading();
            listVk.addAll(groupInteractor.vkList());
           /* groupInteractor.getGroupsListFromDb().subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                @Override
                public void onSuccess(List<GroupModel> groupModelList) {
                    queryDbList.clear();
                    queryDbList.addAll(groupModelList);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }
            });
            groupInteractor.getFavorite().subscribe(new DisposableSingleObserver<List<GroupModel>>() {
                @Override
                public void onSuccess(List<GroupModel> groupModelList) {
                    queryDbListFavorite.clear();
                    queryDbListFavorite.addAll(groupModelList);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }
            });
            if (queryDbList != null) {
                groupInteractor.deleteAll(queryDbList);
            }*/
            groupInteractor.insertVkInDb(listVk);
          //  groupInteractor.updateFavoriteList(queryDbListFavorite);
        }
    }

    public void loadGroupsFromDb() {
        disposable = groupInteractor.getAllGroupsFromDb()
                .doOnSubscribe(subscription -> getViewState().startLoading())
                .subscribe(this::groupsLoaded);
    }

    public void groupsLoaded(List<GroupModel> groupModelList) {
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
        groupInteractor.disposeAll();
    }
}
