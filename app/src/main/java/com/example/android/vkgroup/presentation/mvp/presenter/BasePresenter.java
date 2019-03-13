package com.example.android.vkgroup.presentation.mvp.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<View extends MvpView>extends MvpPresenter<View> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void addSubscription(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    public void unsubscribe(){
        compositeDisposable.clear();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }
}