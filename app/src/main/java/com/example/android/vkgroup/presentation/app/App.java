package com.example.android.vkgroup.presentation.app;

import android.app.Application;

import com.example.android.vkgroup.presentation.di.component.AppComponent;
import com.example.android.vkgroup.presentation.di.component.DaggerAppComponent;
import com.example.android.vkgroup.presentation.di.module.AppModule;
import com.example.android.vkgroup.presentation.di.module.ContextModule;
import com.example.android.vkgroup.presentation.di.module.GroupModule;
import com.example.android.vkgroup.presentation.di.module.RoomModule;
import com.vk.sdk.VKSdk;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .roomModule(new RoomModule(this))
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(getApplicationContext()))
                .groupModule(new GroupModule())
                .build();

        VKSdk.initialize(getApplicationContext());
    }

    public static AppComponent getComponent() {
        return component;
    }
}

