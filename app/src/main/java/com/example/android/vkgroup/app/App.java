package com.example.android.vkgroup.app;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.android.vkgroup.di.component.AppComponent;
import com.example.android.vkgroup.di.component.DaggerAppComponent;
import com.example.android.vkgroup.di.module.AppModule;
import com.example.android.vkgroup.di.module.ContextModule;
import com.example.android.vkgroup.di.module.GroupModule;
import com.example.android.vkgroup.di.module.RoomModule;
import com.example.android.vkgroup.model.AppDatabase;
import com.vk.sdk.VKSdk;

public class App extends Application {

    private static AppComponent component;
   // public static App instance;

   // private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
       /* instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();*/

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

  /*  public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }*/


}

