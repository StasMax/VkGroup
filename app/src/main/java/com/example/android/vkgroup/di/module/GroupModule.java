package com.example.android.vkgroup.di.module;

import com.example.android.vkgroup.adapter.GroupAdapter;
import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.presenter.GroupPresenter;
import com.example.android.vkgroup.provider.GroupDbProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupModule {

    @ApplicationScope
    @Provides
    GroupPresenter dbPresenter(){
        return new GroupPresenter();
    }

    @ApplicationScope
    @Provides
    GroupDbProvider groupDbProvider(GroupPresenter dbPresenter){
        return new GroupDbProvider(dbPresenter);
    }

    @Provides
    GroupAdapter groupAdapter(){
       return new GroupAdapter();
    }
}
