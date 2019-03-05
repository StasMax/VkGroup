package com.example.android.vkgroup.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface ModelDao {
    @Query("SELECT * FROM dbGroups")
    Flowable<List<GroupModel>> getAll();

    @Query("SELECT * FROM dbGroups")
    List<GroupModel> getAllList();

    @Query("SELECT * FROM dbGroups WHERE isFavorite LIKE:isFavorite")
    Flowable<List<GroupModel>> getByFavorite(Boolean isFavorite);

    @Query("SELECT * FROM dbGroups WHERE isFavorite LIKE :isFavorite")
    Single<List<GroupModel>> getByFavoriteSingle(Boolean isFavorite);

    @Query("SELECT * FROM dbGroups WHERE isFavorite LIKE :isFavorite")
    List<GroupModel> getByFavoriteList(Boolean isFavorite);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(GroupModel groupModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(GroupModel... groupModels);

    @Delete
    void deleteAll(List<GroupModel> groupModelList);

    @Update
    void update(GroupModel groupModel);

    @Update
    void updateFavorite(List<GroupModel> groupModelListFavorite);

}
