package com.example.android.vkgroup.presentation.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)

public interface GroupView extends MvpView {
    void startLoading();
    void endLoading();
    void showError(int textResource);
    void setupEmptyList();
    void setupGroupsList();

}