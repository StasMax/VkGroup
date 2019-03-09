package com.example.android.vkgroup.presentation.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.presentation.app.App;
import com.example.android.vkgroup.data.model.AppDatabase;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.R;
import com.example.android.vkgroup.data.provider.GroupDbProvider;
import com.example.android.vkgroup.presentation.mvp.view.GroupView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.vkgroup.presentation.helper.Helper.isOnline;

@InjectViewState
public class GroupPresenter extends MvpPresenter<GroupView> {
    @Inject
    GroupDbProvider groupDbProvider;
    @Inject
    GroupAdapterRv groupAdapterRv;
    @Inject
    Context appContext;
    @Inject
    AppDatabase providesRoomDatabase;
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
