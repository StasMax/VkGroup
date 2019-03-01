package com.example.android.vkgroup.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface LoginView extends MvpView {
    void startLoading();

    void endLoading();

    void showError(int text);

    void openGroups();
}
