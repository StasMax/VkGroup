package com.example.android.vkgroup.helpers;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class MyApplication extends Application {

  /*  public static MyApplication instance;
    private AppDatabase database;*/

    @Override
    public void onCreate() {
        super.onCreate();

       /* instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "populus-database")
                .build();*/

        VKSdk.initialize(getApplicationContext());
    }

  /*  public static MyApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }*/

}

