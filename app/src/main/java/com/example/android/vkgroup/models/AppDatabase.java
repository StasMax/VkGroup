package com.example.android.vkgroup.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.arch.persistence.room.*;

import com.example.android.vkgroup.activities.GroupActivity;
import com.example.android.vkgroup.helpers.MyApplication;
import com.example.android.vkgroup.providers.GroupDbProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@Database(entities = {GroupModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    static List<GroupModel> mGroupModelList = new ArrayList<>();


    public abstract ModelDao getModelDao();


    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getINSTANCE() {
        return INSTANCE;
    }

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public static void listDb(List<GroupModel> groupModelList) {
        for (GroupModel groupModel : groupModelList
        ) {
            INSTANCE.getModelDao().insertAll(groupModel);

        }
    }

    public static List<GroupModel> loadLstDb() {
        mGroupModelList.addAll(INSTANCE.getModelDao().getAll1());
        return mGroupModelList;
    }

    public static void deleteAllDb(List<GroupModel> groupModelList) {
        INSTANCE.getModelDao().deleteAll(groupModelList);
    }


    public static void updateGmList(List<GroupModel> groupModelList) {
        INSTANCE.getModelDao().updateFavorite(groupModelList);
    }

    public static void setFavorite(final GroupModel groupModel) {
        Callable<Void> clb = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                INSTANCE.getModelDao().insertFavorite(groupModel);
                return null;
            }
        };
        Disposable mCompletable = Completable.fromCallable(clb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    public static void setOutFavorite(final GroupModel groupModel) {
        Callable<Void> clb = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                INSTANCE.getModelDao().insertFavorite(groupModel);
                return null;
            }
        };
        Disposable mCompletable = Completable.fromCallable(clb)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

}
