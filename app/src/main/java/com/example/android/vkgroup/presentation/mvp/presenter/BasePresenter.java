package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.example.android.vkgroup.domain.interactor.IGroupInteractor;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

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

}