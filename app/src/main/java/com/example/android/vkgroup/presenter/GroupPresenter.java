package com.example.android.vkgroup.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.app.App;
import com.example.android.vkgroup.model.AppDatabase;
import com.example.android.vkgroup.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.provider.GroupDbProvider;
import com.example.android.vkgroup.view.GroupView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.vkgroup.helper.Helper.isOnline;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {
    @Inject
    public GroupDbProvider groupDbProvider;
    @Inject
    GroupAdapterRv groupAdapterRv;
    @Inject
    public Context appContext;
    @Inject
    public AppDatabase providesRoomDatabase;
    private Disposable disposable;

    public GroupPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadGroupsVk();
    }

    public void loadGroupsVk() {
        getViewState().startLoading();

        if (isOnline(appContext)) {
            groupDbProvider.loadGroupToDb();
        }
    }

    public void loadGroupsFromDb() {
        disposable = providesRoomDatabase.getModelDao().getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::groupsLoaded);
    }

    public void groupsLoaded(List<GroupModel> groupModelList) {
        getViewState().endLoading();
        if (groupModelList.size() == 0) {
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_grous_item);
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
