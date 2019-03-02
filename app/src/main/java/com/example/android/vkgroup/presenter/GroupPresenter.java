package com.example.android.vkgroup.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.android.vkgroup.adapter.GroupAdapter;
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
    public Context appContext;
    @Inject
    public AppDatabase providesRoomDatabase;
    @Inject
    GroupAdapter groupAdapter;
    private Disposable disposable;

    public void loadGroups() {
        getViewState().startLoading();

        if (isOnline(appContext)) {
            groupDbProvider.loadGroupToDb();
        }

        disposable = providesRoomDatabase.getModelDao().getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupModel>>() {
                    @Override
                    public void accept(List<GroupModel> loadGroupList) throws Exception {
                        groupsLoaded(loadGroupList);
                    }
                });
    }

    public void groupsLoaded(List<GroupModel> groupModelList) {
        getViewState().endLoading();
        if (groupModelList.size() == 0) {
            getViewState().setupEmptyList();
            getViewState().showError(R.string.no_grous_item);
        } else
            getViewState().setupGroupsList();
        groupAdapter.setupFavoriteGroups(groupModelList);
    }

    public void showError(int textResource) {
        getViewState().showError(textResource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();

    }
}
