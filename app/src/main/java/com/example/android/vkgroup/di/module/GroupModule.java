package com.example.android.vkgroup.di.module;

import com.example.android.vkgroup.adapter.GroupAdapterRv;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.presenter.GroupPresenter;
import com.example.android.vkgroup.provider.GroupDbProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupModule {


    @Provides
    GroupPresenter dbPresenter(){
        return new GroupPresenter();
    }


  /*  @Provides
    GroupDbProvider groupDbProvider(GroupPresenter dbPresenter){
        return new GroupDbProvider(dbPresenter);
    }*/
  @Provides
  GroupDbProvider groupDbProvider(){
      return new GroupDbProvider();}

    @ApplicationScope
    @Provides
    GroupAdapterRv groupAdapterRv(){
        return new GroupAdapterRv();
    }
}
