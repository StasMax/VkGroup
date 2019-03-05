package com.example.android.vkgroup.di.module;

import android.app.Application;
import android.support.v7.util.DiffUtil;

import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.helper.GroupModelDiffUtilCallback;
import com.example.android.vkgroup.model.GroupModel;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return application;
    }
}
