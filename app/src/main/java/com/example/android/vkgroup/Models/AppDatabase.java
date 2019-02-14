package com.example.android.vkgroup.Models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.vkgroup.helpers.MyApplication;


import java.util.List;

@Database(entities = {GroupModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ModelDao modelDao();



    AppDatabase db = MyApplication.getInstance().getDatabase();
    ModelDao modelDao = db.modelDao();

    public void listDb (List<GroupModel>groupModelList){
        modelDao.insertList(groupModelList);
    }

}
