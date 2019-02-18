package com.example.android.vkgroup.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.arch.persistence.room.*;
import com.example.android.vkgroup.activities.GroupActivity;
import com.example.android.vkgroup.helpers.MyApplication;

import java.util.ArrayList;
import java.util.List;




@Database(entities = {GroupModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
static List<GroupModel>mGroupModelList = new ArrayList<>();
    public abstract ModelDao getModelDao();
   // private AppDatabase db;

  /*  public void dbBegin(Context context){
        if (db == null){

            db = Room.databaseBuilder(context,
                    AppDatabase.class, "populus-database").build();
        }
    }*/
  private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


  //  AppDatabase db = MyApplication.getInstance().getDatabase();
   // ModelDao modelDao = INSTANCE.getModelDao();

    public static void listDb (List<GroupModel>groupModelList){
      //  modelDao.insertList(groupModelList);
      //  INSTANCE.getModelDao().insertList(groupModelList);
        for (GroupModel groupModel:groupModelList
             ) {INSTANCE.getModelDao().insertAll(groupModel);

        }
    }

    public static List<GroupModel> loadLstDb(){
        mGroupModelList.addAll(INSTANCE.getModelDao().getAll1());
        return mGroupModelList;
    }

}
