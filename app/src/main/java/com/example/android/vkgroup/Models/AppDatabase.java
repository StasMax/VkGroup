package com.example.android.vkgroup.Models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.vkgroup.activities.GroupActivity;
import com.example.android.vkgroup.helpers.MyApplication;


import java.util.List;

import static com.vk.sdk.VKUIHelper.getApplicationContext;


@Database(entities = {GroupModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ModelDao getModelDao();
    private AppDatabase db;

    public void dbBegin(Context context){
        if (db == null){

            db = Room.databaseBuilder(context,
                    AppDatabase.class, "populus-database").build();
        }
    }



   // AppDatabase db = MyApplication.getInstance().getDatabase();
    ModelDao modelDao = db.getModelDao();

    public void listDb (List<GroupModel>groupModelList){
        modelDao.insertList(groupModelList);
    }

}
