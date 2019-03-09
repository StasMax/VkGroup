package com.example.android.vkgroup.presentation.di.module;

import android.app.Application;

import com.example.android.vkgroup.presentation.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return application;
    }
}
