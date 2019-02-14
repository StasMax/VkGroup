package com.example.android.vkgroup.helpers;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.vkgroup.Models.AppDatabase;
import com.vk.sdk.VKSdk;

public class MyApplication extends Application {
    public static MyApplication instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
    }
    public static MyApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
