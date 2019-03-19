package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.example.android.vkgroup.data.model.GroupModel;
import com.example.android.vkgroup.domain.interactor.GroupInteractor;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    @Inject
    GroupInteractor groupInteractor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void unsubscribe() {
        compositeDisposable.clear();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    public void favoriteListener(GroupAdapterRv groupAdapterRv) {
        groupAdapterRv.setListener((groupModel, isChecked) -> {
            if (isChecked) {
                groupModel.setFavorite(true);
                addSubscription(groupInteractor.updateFavorite(groupModel)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe());
            } else {
                groupModel.setFavorite(false);
                addSubscription(groupInteractor.updateFavorite(groupModel)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe());
        }


    });
    }
}