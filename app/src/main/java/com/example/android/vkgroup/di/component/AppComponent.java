package com.example.android.vkgroup.di.component;

import com.example.android.vkgroup.data.repository.DataSingleVkRepository;
import com.example.android.vkgroup.domain.interactor.GroupDomainInteractor;
import com.example.android.vkgroup.di.module.AppModule;
import com.example.android.vkgroup.di.module.GroupModule;
import com.example.android.vkgroup.di.module.RoomModule;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.presentation.adapter.ViewHolder;
import com.example.android.vkgroup.presentation.mvp.ui.FavoriteActivity;
import com.example.android.vkgroup.presentation.mvp.ui.GroupActivity;

import dagger.Component;

@ApplicationScope
@Component(dependencies = {}, modules = {RoomModule.class, GroupModule.class, AppModule.class})
public interface AppComponent {

    void inject(GroupActivity groupActivity);

    void inject(FavoriteActivity favoriteActivity);

    void inject(ViewHolder viewHolder);

    void inject(GroupDomainInteractor groupDomainInteractor);

    void inject(DataSingleVkRepository dataSingleVkRepository);
}
