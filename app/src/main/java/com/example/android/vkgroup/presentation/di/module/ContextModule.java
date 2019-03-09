package com.example.android.vkgroup.presentation.di.module;

import android.content.Context;

import com.example.android.vkgroup.presentation.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context provideContext() {
        return context;
    }
}
