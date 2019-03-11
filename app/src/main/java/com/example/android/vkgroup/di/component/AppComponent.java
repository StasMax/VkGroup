package com.example.android.vkgroup.di.component;

import com.example.android.vkgroup.data.repository.DataVkRepository;
import com.example.android.vkgroup.presentation.adapter.GroupAdapterRv;
import com.example.android.vkgroup.di.module.AppModule;
import com.example.android.vkgroup.di.module.ContextModule;
import com.example.android.vkgroup.di.module.GroupModule;
import com.example.android.vkgroup.di.module.RoomModule;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.presentation.mvp.presenter.FavoritePresenter;
import com.example.android.vkgroup.presentation.mvp.presenter.GroupPresenter;
import com.example.android.vkgroup.provider.GroupDbProvider;
import com.example.android.vkgroup.presentation.mvp.ui.FavoriteActivity;
import com.example.android.vkgroup.presentation.mvp.ui.GroupActivity;
import com.example.android.vkgroup.presentation.mvp.ui.StartActivity;

import dagger.Component;

@ApplicationScope
@Component(dependencies = {}, modules = {RoomModule.class, ContextModule.class, GroupModule.class, AppModule.class})
public interface AppComponent {

    void inject(GroupActivity groupActivity);

    void inject(StartActivity startActivity);

    void inject(FavoriteActivity favoriteActivity);

    void inject(GroupPresenter groupPresenter);

    void inject(FavoritePresenter favoritePresenter);

    void inject(GroupDbProvider groupDbProvider);

    void inject(GroupAdapterRv groupAdapter);

    void inject(DataVkRepository dataVkRepository);
}
