package com.example.android.vkgroup.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.vkgroup.di.scope.ApplicationScope;
import com.example.android.vkgroup.data.model.AppDatabase;
import com.example.android.vkgroup.data.model.ModelDao;
import com.example.android.vkgroup.data.model.ModelDataSource;
import com.example.android.vkgroup.data.model.ModelRepository;


import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
   private AppDatabase database;

    @ApplicationScope
   public RoomModule(Application application) {
        database = Room.databaseBuilder(application.getApplicationContext(), AppDatabase.class, "app_database").build();
    }
    @ApplicationScope
    @Provides
    AppDatabase providesRoomDatabase(){
       return database;
    }

    @ApplicationScope
    @Provides
    ModelDao providesModelDao(AppDatabase database) {
        return database.getModelDao();
    }

    @ApplicationScope
    @Provides
    ModelRepository modelRepository(ModelDao modelDao) {
        return new ModelDataSource(modelDao);
    }
  }
