package com.example.android.vkgroup.di.module;

import android.app.Application;

import com.example.android.vkgroup.di.scope.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return mApplication;
    }

}
