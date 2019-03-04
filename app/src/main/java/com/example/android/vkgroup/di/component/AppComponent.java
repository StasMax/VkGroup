package com.example.android.vkgroup.di.component;

import android.app.Application;
import android.content.Context;

import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.di.module.AppModule;
import com.example.android.vkgroup.di.module.ContextModule;
import com.example.android.vkgroup.di.module.GroupModule;
import com.example.android.vkgroup.di.module.RoomModule;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.model.AppDatabase;
import com.example.android.vkgroup.model.ModelDao;
import com.example.android.vkgroup.model.ModelDataSource;
import com.example.android.vkgroup.model.ModelRepository;
import com.example.android.vkgroup.presenter.FavoritePresenter;
import com.example.android.vkgroup.presenter.GroupPresenter;
import com.example.android.vkgroup.provider.GroupDbProvider;
import com.example.android.vkgroup.ui.FavoriteActivity;
import com.example.android.vkgroup.ui.GroupActivity;
import com.example.android.vkgroup.ui.StartActivity;

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

  //  void inject(ModelDataSource modelDataSource);
    void inject(GroupAdapterRv groupAdapter);


    ModelDao modelDao();

    AppDatabase appDatabase();

    ModelRepository modelRepository();

    Context appContext();

    Application application();

    GroupDbProvider groupDbProvider();

    GroupPresenter groupPresenter();



    GroupAdapterRv groupAdapterRv();

  //  ModelDataSource modelDataSource();

}
