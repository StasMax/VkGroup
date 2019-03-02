package com.example.android.vkgroup.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.android.vkgroup.model.GroupModel;

import java.util.List;

@StateStrategyType(value = AddToEndSingleStrategy.class)

public interface GroupView extends MvpView {
    void startLoading();
    void endLoading();
    void showError(int textResource);
    void setupEmptyList();
    void setupGroupsList();

}
