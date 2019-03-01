package com.example.android.vkgroup.app;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class MyApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();

        VKSdk.initialize(getApplicationContext());
    }

    public static AppComponent getComponent() {
        return component;
    }
}

